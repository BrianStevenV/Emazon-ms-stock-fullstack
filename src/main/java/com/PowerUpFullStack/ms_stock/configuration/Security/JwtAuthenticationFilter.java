package com.PowerUpFullStack.ms_stock.configuration.Security;

import com.PowerUpFullStack.ms_stock.configuration.Security.jwt.JwtAuthenticationToken;
import com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity;
import com.PowerUpFullStack.ms_stock.configuration.Security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.PowerUpFullStack.ms_stock.configuration.Security.jwt.utils.JwtMethodUtils.getRoleFromToken;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.SecurityUtils.getToken;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private List<String> excludedPrefixes = Arrays.asList(
            ConstantsSecurity.SWAGGER_UI_HTML,ConstantsSecurity.SWAGGER_UI,
            ConstantsSecurity.V3_API_DOCS,ConstantsSecurity.ACTUATOR_HEALTH,
            ConstantsSecurity.CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY,
            ConstantsSecurity.BRAND_CONTROLLER_GET_PAGINATION_BRAND,
            ConstantsSecurity.PRODUCT_CONTROLLER_GET_PAGINATION_PRODUCT);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getToken(request);
            System.out.println(token);
            if(token != null){

                Authentication authentication = new JwtAuthenticationToken(token, getRoleFromToken(token));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }   catch (Exception e){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        return SecurityUtils.isExcludedPrefixRecursively(currentRoute, excludedPrefixes);
    }
}
