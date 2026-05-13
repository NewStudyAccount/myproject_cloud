package com.project.cloud.common.security.filter;

import com.project.cloud.common.core.constant.Constants;
import com.project.cloud.common.redis.service.RedisService;
import com.project.cloud.common.security.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if (StringUtils.hasText(token) && tokenUtils.validateToken(token)) {
                Map<String, Object> claims = tokenUtils.parseToken(token);

                Long userId = claims.get(Constants.LOGIN_USER_ID) != null
                        ? Long.parseLong(claims.get(Constants.LOGIN_USER_ID).toString()) : null;
                String username = claims.get(Constants.LOGIN_USERNAME) != null
                        ? claims.get(Constants.LOGIN_USERNAME).toString() : null;

                if (userId != null) {
                    String tokenKey = Constants.TOKEN_KEY + userId;
                    String storedToken = redisService.get(tokenKey);
                    if (storedToken == null || !storedToken.equals(token)) {
                        log.debug("Token已失效或已登出, userId: {}", userId);
                        filterChain.doFilter(request, response);
                        return;
                    }
                }

                @SuppressWarnings("unchecked")
                List<String> authorities = claims.get(Constants.LOGIN_AUTHORITIES) != null
                        ? (List<String>) claims.get(Constants.LOGIN_AUTHORITIES) : List.of();

                List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, grantedAuthorities);
                authentication.setDetails(username);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Token解析失败", e);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_PREFIX)) {
            return bearerToken.substring(Constants.TOKEN_PREFIX.length());
        }
        return null;
    }
}