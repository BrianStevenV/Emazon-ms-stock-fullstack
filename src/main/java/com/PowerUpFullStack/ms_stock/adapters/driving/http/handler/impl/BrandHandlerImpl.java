package com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.impl;

import com.PowerUpFullStack.ms_stock.adapters.driving.http.handler.IBrandHandler;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandRequestMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IBrandResponseMapper;
import com.PowerUpFullStack.ms_stock.adapters.driving.http.mappers.IParametersOfPaginationRequestMapper;
import com.PowerUpFullStack.ms_stock.domain.api.IBrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandHandlerImpl implements IBrandHandler {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;
    private final IParametersOfPaginationRequestMapper parametersOfPaginationRequestMapper;
}
