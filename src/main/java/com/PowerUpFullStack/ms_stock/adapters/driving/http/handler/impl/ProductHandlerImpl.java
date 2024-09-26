package com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.impl;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.IProductHandler;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IProductResponseMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IProductServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductHandlerImpl implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;

}
