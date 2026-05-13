package com.project.cloud.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Value("${token.secret:abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ}")
    private String secret;

    private static final List<String> WHITE_LIST = List.of(
            "/auth/login",
            "/auth/captcha",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/doc.html",
            "/webjars/**"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 白名单放行
        for (String pattern : WHITE_LIST) {
            if (pathMatcher.match(pattern, path)) {
                return chain.filter(exchange);
            }
        }

        // 验证Token
        String token = resolveToken(request);
        if (!StringUtils.hasText(token)) {
            return unauthorized(exchange, "令牌不能为空");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 将用户信息传递给下游服务
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", claims.get("user_id") != null ? claims.get("user_id").toString() : "")
                    .header("X-Username", claims.get("username") != null ? claims.get("username").toString() : "")
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            return unauthorized(exchange, "令牌无效或已过期");
        }
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"code\":401,\"msg\":\"" + message + "\",\"data\":null}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
