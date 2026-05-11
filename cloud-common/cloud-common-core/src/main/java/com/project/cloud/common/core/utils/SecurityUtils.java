package com.project.cloud.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全工具类
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取 Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        // TODO: 从 Authentication 中获取用户ID
        return null;
    }

    /**
     * 密码加密
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * 密码匹配
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 判断是否为内部请求
     */
    public static boolean isInner() {
        // TODO: 判断是否为内部请求
        return false;
    }

    private SecurityUtils() {
    }
}
