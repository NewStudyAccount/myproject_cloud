package com.project.cloud.common.security.utils;

import com.project.cloud.common.core.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long userId) {
            return userId;
        }
        throw new BusinessException(401, "未登录");
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof String username) {
            return username;
        }
        throw new BusinessException(401, "未登录");
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities();
        }
        return List.of();
    }

    public static boolean hasAuthority(String authority) {
        return getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

    private SecurityUtils() {
    }
}
