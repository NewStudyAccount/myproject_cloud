package com.project.cloud.common.core.exception;

import com.project.cloud.common.core.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常: {} - {}", request.getRequestURI(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数校验失败";
        return Result.error(message);
    }

    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数绑定失败";
        return Result.error(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return Result.error("不支持'" + e.getMethod() + "'请求方式");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> handleNoResourceFound(NoResourceFoundException e) {
        return Result.error(404, "资源不存在");
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {}", request.getRequestURI(), e);
        return Result.error("系统异常，请联系管理员");
    }
}
