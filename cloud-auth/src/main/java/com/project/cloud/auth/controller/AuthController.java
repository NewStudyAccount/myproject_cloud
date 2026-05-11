package com.project.cloud.auth.controller;

import com.project.cloud.auth.domain.LoginRequest;
import com.project.cloud.auth.domain.LoginResponse;
import com.project.cloud.auth.service.AuthService;
import com.project.cloud.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新 Token")
    public Result<LoginResponse> refresh() {
        return Result.success(authService.refreshToken());
    }
}
