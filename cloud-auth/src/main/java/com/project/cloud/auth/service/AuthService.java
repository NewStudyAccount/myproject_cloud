package com.project.cloud.auth.service;

import com.project.cloud.auth.domain.LoginRequest;
import com.project.cloud.auth.domain.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout();

    LoginResponse refresh(String refreshToken);
}
