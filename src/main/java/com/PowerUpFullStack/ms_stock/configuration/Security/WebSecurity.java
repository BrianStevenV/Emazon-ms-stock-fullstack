package com.PowerUpFullStack.ms_stock.configuration.Security;

import com.PowerUpFullStack.ms_stock.configuration.Security.jwt.JwtEntryPoint;
import com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity {


    @Autowired
    JwtEntryPoint jwtEntryPoint;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    };


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() { return new JwtAuthenticationFilter(); };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(ConstantsSecurity.SWAGGER_UI_HTML, ConstantsSecurity.SWAGGER_UI,
                                ConstantsSecurity.V3_API_DOCS, ConstantsSecurity.ACTUATOR_HEALTH,
                                ConstantsSecurity.CATEGORY_CONTROLLER_GET_PAGINATION_CATEGORY,
                                ConstantsSecurity.BRAND_CONTROLLER_GET_PAGINATION_BRAND,
                                ConstantsSecurity.PRODUCT_CONTROLLER_GET_PAGINATION_PRODUCT)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, ConstantsSecurity.CATEGORY_CONTROLLER_POST_CREATE_CATEGORY,
                                ConstantsSecurity.BRAND_CONTROLLER_POST_CREATE_BRAND,
                                ConstantsSecurity.PRODUCT_CONTROLLER_POST_CREATE_PRODUCT)
                        .hasAuthority(ConstantsSecurity.ADMINISTRATOR_ROLE)
                        .requestMatchers(HttpMethod.PATCH, ConstantsSecurity.PRODUCT_CONTROLLER_PATCH_UPDATE_AMOUNT,
                                ConstantsSecurity.PRODUCT_CONTROLLER_PATCH_CANCEL_AMOUNT)
                        .hasAuthority(ConstantsSecurity.WAREHOUSE_ASSISTANT_ROLE)
                        .requestMatchers(HttpMethod.GET,
                                ConstantsSecurity.PRODUCT_CONTROLLER_GET_PRODUCT_BY_ID)
                        .hasAuthority(ConstantsSecurity.CUSTOMER_ROLE)
                        .requestMatchers(HttpMethod.POST,
                                ConstantsSecurity.PRODUCT_CONTROLLER_POST_CATEGORIES_BY_PRODUCTS_IDS,
                                ConstantsSecurity.PRODUCT_CONTROLLER_POST_AMOUNT_STOCK_AVAILABLE,
                                ConstantsSecurity.PRODUCT_CONTROLLER_POST_REDUCE_QUANTITY,
                                ConstantsSecurity.PRODUCT_CONTROLLER_POST_PRODUCT_BY_PRODUCTS_IDS)
                        .hasAuthority(ConstantsSecurity.CUSTOMER_ROLE)
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
