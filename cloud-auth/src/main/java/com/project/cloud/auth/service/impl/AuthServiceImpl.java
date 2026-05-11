package com.project.cloud.auth.service.impl;

import com.project.cloud.auth.domain.LoginRequest;
import com.project.cloud.auth.domain.LoginResponse;
import com.project.cloud.auth.service.AuthService;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.redis.service.RedisService;
import com.project.cloud.common.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisService redisService;

    private static final String LOGIN_TOKEN_KEY = "login_tokens:";
    private static final long TOKEN_EXPIRE_TIME = 7200; // 2小时

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            // 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 设置认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成 Token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", request.getUsername());
            String token = jwtUtils.generateToken(request.getUsername(), claims);

            // 存储 Token 到 Redis
            String redisKey = LOGIN_TOKEN_KEY + request.getUsername();
            redisService.set(redisKey, token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

            log.info("用户 {} 登录成功", request.getUsername());

            return LoginResponse.builder()
                    .accessToken(token)
                    .refreshToken(token)
                    .tokenType("Bearer")
                    .expiresIn(TOKEN_EXPIRE_TIME)
                    .build();
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            throw new BusinessException("登录失败: " + e.getMessage());
        }
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            String redisKey = LOGIN_TOKEN_KEY + username;
            redisService.delete(redisKey);
            SecurityContextHolder.clearContext();
            log.info("用户 {} 登出成功", username);
        }
    }

    @Override
    public LoginResponse refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("用户未登录");
        }

        String username = authentication.getName();

        // 生成新 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        String token = jwtUtils.generateToken(username, claims);

        // 更新 Redis 中的 Token
        String redisKey = LOGIN_TOKEN_KEY + username;
        redisService.set(redisKey, token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken(token)
                .tokenType("Bearer")
                .expiresIn(TOKEN_EXPIRE_TIME)
                .build();
    }
}
