package com.PowerUpFullStack.ms_stock.configuration.Security.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import java.util.List;

import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.AUTHORIZATION_HEADER;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.AUTHORIZATION_HEADER_SUBSTRING;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.BEARER_TOKEN;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.PREFIX_RECURSIVE;
import static com.PowerUpFullStack.ms_stock.configuration.Security.utils.ConstantsSecurity.PREFIX_RECURSIVE_NEXT;

public class SecurityUtils {

    private static AntPathMatcher pathMatcher = new AntPathMatcher();

    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_TOKEN)) {
            return header.substring(AUTHORIZATION_HEADER_SUBSTRING);
        }
        return null;
    }


    public static boolean isExcludedPrefixRecursively(String currentRoute, List<String> prefixes) {
        if (prefixes.isEmpty()) {
            return false;
        }

        String prefix = prefixes.get(PREFIX_RECURSIVE);
        if (pathMatcher.matchStart(prefix, currentRoute)) {
            return true;
        } else {

            return isExcludedPrefixRecursively(currentRoute, prefixes.subList(PREFIX_RECURSIVE_NEXT, prefixes.size()));
        }
    }
}
