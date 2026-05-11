package com.project.cloud.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 网关全局异常处理
 */
@Slf4j
@Order(-1)
@Component
@RequiredArgsConstructor
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 获取状态码
        int code;
        String message;
        if (ex instanceof ResponseStatusException responseStatusException) {
            code = responseStatusException.getStatusCode().value();
            message = responseStatusException.getReason();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "网关内部错误";
        }

        log.error("网关异常: {} - {}", exchange.getRequest().getPath(), ex.getMessage(), ex);

        // 构建响应
        response.setStatusCode(HttpStatus.valueOf(code));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", message);
        result.put("data", null);

        try {
            String json = objectMapper.writeValueAsString(result);
            DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("序列化响应失败", e);
            return response.setComplete();
        }
    }
}
