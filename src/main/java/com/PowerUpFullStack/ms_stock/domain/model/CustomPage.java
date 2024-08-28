package com.PowerUpFullStack.ms_stock.domain.model;
import java.util.List;

public class CustomPage<Category> {
    private List<Category> content;         // Contenido de la página
    private int pageNumber;          // Número de la página actual
    private int pageSize;            // Tamaño de la página
    private long totalElements;      // Número total de elementos
    private int totalPages;          // Número total de páginas
    private boolean isFirst;         // Es la primera página?
    private boolean isLast;

    public CustomPage(List<Category> content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean isFirst, boolean isLast) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }
    public CustomPage() {}

    public List<Category> getContent() {
        return content;
    }

    public void setContent(List<Category> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
