package com.PowerUpFullStack.ms_stock.domain.usecase;

import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import com.PowerUpFullStack.ms_stock.domain.exception.BrandNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.exception.ProductNotFoundException;
import com.PowerUpFullStack.ms_stock.domain.model.AllCategories;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStock;
import com.PowerUpFullStack.ms_stock.domain.model.AmountStockAvailable;
import com.PowerUpFullStack.ms_stock.domain.model.Brand;
import com.PowerUpFullStack.ms_stock.domain.model.Category;
import com.PowerUpFullStack.ms_stock.domain.model.CustomPage;
import com.PowerUpFullStack.ms_stock.domain.model.FilterBy;
import com.PowerUpFullStack.ms_stock.domain.model.Product;
import com.PowerUpFullStack.ms_stock.domain.model.ProductAmount;
import com.PowerUpFullStack.ms_stock.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_stock.domain.model.ProductIds;
import com.PowerUpFullStack.ms_stock.domain.model.ProductSalesDetails;
import com.PowerUpFullStack.ms_stock.domain.model.Products;
import com.PowerUpFullStack.ms_stock.domain.model.ReduceQuantity;
import com.PowerUpFullStack.ms_stock.domain.model.Sale;
import com.PowerUpFullStack.ms_stock.domain.model.SortDirection;
import com.PowerUpFullStack.ms_stock.domain.spi.IBrandPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.ICategoryPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.spi.IProductPersistencePort;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ProductUseCaseUtils;
import com.PowerUpFullStack.ms_stock.domain.usecase.utils.ValidationDomainUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_stock.domain.usecase.utils.ConstantsDomain.COMPARISON_RESULT_INIT;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort,
                          ICategoryPersistencePort categoryPersistencePort,
                          IBrandPersistencePort brandPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createProduct(Product product) {
        product.setCategories(ProductUseCaseUtils.validateCategories(product.getCategoryId(), categoryPersistencePort));
        product.setBrand(brandFindById(product.getBrandId()));

        productPersistencePort.saveProduct(product);
    }

    @Override
    public CustomPage<Product> getPaginationProductsByAscAndDescByNameProductOrNameBrandOrCategories(SortDirection sortDirection, FilterBy filterBy) {
        CustomPage<Product> customPage = productPersistencePort.getPaginationProduct();

        List<Product> products = customPage.getContent();

        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> {
                    int comparisonResult = COMPARISON_RESULT_INIT;

                    switch (filterBy) {
                        case PRODUCT:
                            comparisonResult = p1.getName().compareToIgnoreCase(p2.getName());
                            break;
                        case BRAND:
                            comparisonResult = p1.getBrand().getName().compareToIgnoreCase(p2.getBrand().getName());
                            break;
                        case CATEGORY:
                            comparisonResult = ValidationDomainUtils.compareCategories(p1.getCategories(), p2.getCategories());
                            break;
                    }

                    return sortDirection == SortDirection.ASC ? comparisonResult : -comparisonResult;
                })
                .collect(Collectors.toList());

        return new CustomPage<>(
                sortedProducts,
                customPage.getPageNumber(),
                customPage.getPageSize(),
                customPage.getTotalElements(),
                customPage.getTotalPages(),
                customPage.isFirst(),
                customPage.isLast()
        );
    }

    @Override
    public void updateProductAmountFromSupplies(ProductAmount productAmount) {
        ProductUseCaseUtils.validateProductAmount(productAmount);
        updateProductAmount(productAmount.getProductId(), productAmount.getAmount());
    }

    @Override
    public void revertProductAmountFromSupplies(ProductAmount productAmount) {
        ProductUseCaseUtils.validateProductAmount(productAmount);
        updateProductAmount(productAmount.getProductId(), -productAmount.getAmount());
    }

    @Override
    public ProductCategory getCategoryFromProductById(long id) {
        //ESTO DEBERIA SER OPTIONAL
        return new ProductCategory(productFindById(id).getCategories().stream().map(Category::getId).collect(Collectors.toList()));
    }



    @Override
    public AllCategories getCategoriesByProductIds(ProductIds productIds) {
        return new AllCategories(
                productPersistencePort.getProductsByProductIds(productIds).stream().collect(Collectors.toMap(
                        Product::getId,
                        product -> product.getCategories().stream()
                                .map(Category::getId)
                                .collect(Collectors.toList())
                ))
        );
    }

    @Override
    public AmountStockAvailable getAmountStockAvailable(AmountStock amountStock) {
        return new AmountStockAvailable(productFindById(amountStock.getProductId()).getAmount() >= amountStock.getAmount());
    }

    @Override
    public List<Products> getProductsByProductsIds(ProductIds productIds) {
        return productPersistencePort.getProductsByProductIds(productIds).stream()
                .map(product -> new Products(
                        product.getId(),
                        product.getName(),
                        product.getAmount(),
                        product.getPrice(),
                        product.getBrand().getName(),
                        product.getCategories().stream().map(Category::getName).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public Sale reduceStockQuantity(ReduceQuantity reduceQuantity) {

        Sale saleResponse = new Sale();
        AtomicReference<Double> subtotal = new AtomicReference<>(0.0);
        List<ProductSalesDetails> productSalesDetailsList = new ArrayList<>();
        List<Product> originalStateProducts = new ArrayList<>();
        List<Product> updatedProducts = new ArrayList<>();

        try {
            reduceQuantity.getProductAndQuantityList().forEach(productAndQuantity -> {
                Product product = productFindById(productAndQuantity.getProductId());

                originalStateProducts.add(product);

                if (product.getAmount() < productAndQuantity.getQuantity()) {
                    throw new IllegalArgumentException("The product with id " + productAndQuantity.getProductId() + " has not enough stock");
                }

                product.setAmount(product.getAmount() - productAndQuantity.getQuantity());
                updatedProducts.add(product);

                ProductSalesDetails productSalesDetails = createProductSalesDetails(product);
                productSalesDetailsList.add(productSalesDetails);

                double productTotal = productAndQuantity.getQuantity() * product.getPrice();
                subtotal.updateAndGet(value -> value + productTotal);
            });

            productPersistencePort.saveProducts(updatedProducts);

            saleResponse.setProductSalesDetailsList(productSalesDetailsList);
            saleResponse.setSubtotal(subtotal.get());

            return saleResponse;

        } catch (Exception e) {

            originalStateProducts.forEach(originalProduct -> {
                Product product = productFindById(originalProduct.getId());
                product.setAmount(originalProduct.getAmount());
            });


            productPersistencePort.saveProducts(originalStateProducts);

            throw e;
        }
    }




    private ProductSalesDetails createProductSalesDetails(Product product) {
        return new ProductSalesDetails(product.getId(), product.getAmount(), product.getPrice());
    }


    private void updateProductAmount(Long productId, int amountChange) {
        Product product = productFindById(productId);
        product.setAmount(ProductUseCaseUtils.calculateNewAmount(product.getAmount(), amountChange));
        productPersistencePort.saveProduct(product);
    }

    private Product productFindById(Long productId) {
        return productPersistencePort.getProductById(productId).orElseThrow(ProductNotFoundException::new);
    }

    private Brand brandFindById(Long brandId) {
        return brandPersistencePort.findById(brandId).orElseThrow(BrandNotFoundException::new);
    }

}

