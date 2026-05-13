package com.project.cloud.auth.service.impl;

import com.project.cloud.auth.domain.LoginRequest;
import com.project.cloud.auth.domain.LoginResponse;
import com.project.cloud.auth.service.AuthService;
import com.project.cloud.common.core.constant.Constants;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.redis.service.RedisService;
import com.project.cloud.common.security.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final RedisService redisService;

    private static final long ACCESS_TOKEN_EXPIRE_SECONDS = 7200L;
    private static final long REFRESH_TOKEN_EXPIRE_SECONDS = 604800L;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            Map<String, Object> claims = new HashMap<>();
            claims.put(Constants.LOGIN_USER_ID, userDetails.getUserId());
            claims.put(Constants.LOGIN_USERNAME, userDetails.getUsername());
            claims.put(Constants.LOGIN_AUTHORITIES, authorities);

            String accessToken = tokenUtils.createToken(claims);

            String refreshToken = UUID.randomUUID().toString().replace("-", "");

            String tokenKey = Constants.TOKEN_KEY + userDetails.getUserId();
            redisService.set(tokenKey, accessToken, ACCESS_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);

            String refreshKey = Constants.TOKEN_KEY + "refresh:" + refreshToken;
            Map<String, Object> refreshData = new HashMap<>();
            refreshData.put(Constants.LOGIN_USER_ID, userDetails.getUserId());
            refreshData.put(Constants.LOGIN_USERNAME, userDetails.getUsername());
            redisService.set(refreshKey, claims, REFRESH_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(ACCESS_TOKEN_EXPIRE_SECONDS)
                    .tokenType("Bearer")
                    .build();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public void logout() {
        try {
            Long userId = com.project.cloud.common.security.utils.SecurityUtils.getUserId();
            redisService.delete(Constants.TOKEN_KEY + userId);
        } catch (Exception e) {
            log.warn("登出时清理Token失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoginResponse refresh(String refreshToken) {
        String refreshKey = Constants.TOKEN_KEY + "refresh:" + refreshToken;
        Map<String, Object> claims = redisService.get(refreshKey);
        if (claims == null) {
            throw new BusinessException("刷新Token无效或已过期");
        }

        redisService.delete(refreshKey);

        String newAccessToken = tokenUtils.createToken(claims);

        Long userId = claims.get(Constants.LOGIN_USER_ID) != null
                ? Long.parseLong(claims.get(Constants.LOGIN_USER_ID).toString()) : null;
        if (userId != null) {
            String tokenKey = Constants.TOKEN_KEY + userId;
            redisService.set(tokenKey, newAccessToken, ACCESS_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);
        }

        String newRefreshToken = UUID.randomUUID().toString().replace("-", "");
        String newRefreshKey = Constants.TOKEN_KEY + "refresh:" + newRefreshToken;
        redisService.set(newRefreshKey, claims, REFRESH_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(ACCESS_TOKEN_EXPIRE_SECONDS)
                .tokenType("Bearer")
                .build();
    }
}