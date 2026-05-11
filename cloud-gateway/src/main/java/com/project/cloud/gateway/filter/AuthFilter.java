package com.project.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 认证过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 白名单路径
     */
    private static final List<String> WHITELIST = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/captcha",
            "/oauth2/token",
            "/oauth2/authorize",
            "/swagger-ui",
            "/v3/api-docs",
            "/doc.html",
            "/webjars",
            "/actuator"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 检查是否在白名单中
        if (isWhitelist(path)) {
            return chain.filter(exchange);
        }

        // 检查是否有 Token
        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            log.warn("请求路径 {} 缺少 Token", path);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // TODO: 验证 Token 有效性
        // 将用户信息添加到请求头中
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-Token", token)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isWhitelist(String path) {
        return WHITELIST.stream().anyMatch(path::startsWith);
    }

    private String getToken(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
