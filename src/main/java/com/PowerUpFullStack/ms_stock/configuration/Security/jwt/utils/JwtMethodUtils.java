package com.PowerUpFullStack.ms_stock.configuration.Security.jwt.utils;

import com.PowerUpFullStack.ms_stock.configuration.Security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.ROLES_CLAIM_JWT;

@Component
public class JwtMethodUtils {
    public JwtMethodUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private static JwtConfig jwtConfig;

    public static String getUsernameFromToken(String token) {
        String secret = jwtConfig.getSecret();
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public static List<String> getRoleFromToken(String token){
        String secret = jwtConfig.getSecret();
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build().parseClaimsJws(token)
                .getBody().get(ROLES_CLAIM_JWT, List.class);
    }


}
