package com.project.cloud.gateway.handler;

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

@Slf4j
@Order(-1)
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        int code;
        String message;

        if (ex instanceof ResponseStatusException statusException) {
            code = statusException.getStatusCode().value();
            message = statusException.getReason();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "网关异常，请联系管理员";
        }

        log.error("网关异常: {} - {}", exchange.getRequest().getURI(), ex.getMessage());

        response.setStatusCode(HttpStatus.valueOf(code));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"code\":" + code + ",\"msg\":\"" + message + "\",\"data\":null}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
