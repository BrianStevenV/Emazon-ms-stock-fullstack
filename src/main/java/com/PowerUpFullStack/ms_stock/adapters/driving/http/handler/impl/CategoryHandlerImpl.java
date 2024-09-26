package com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.impl;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.ICategoryHandler;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.ICategoryResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;
}
