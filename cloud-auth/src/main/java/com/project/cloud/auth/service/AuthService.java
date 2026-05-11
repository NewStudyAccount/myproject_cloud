package com.project.cloud.auth.service;

import com.project.cloud.auth.domain.LoginRequest;
import com.project.cloud.auth.domain.LoginResponse;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 登出
     */
    void logout();

    /**
     * 刷新 Token
     */
    LoginResponse refreshToken();
}
