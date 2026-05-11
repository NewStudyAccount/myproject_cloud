package com.project.cloud.common.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.utils.SecurityUtils;
import com.project.cloud.common.log.annotation.OperLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * 操作日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperLogAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint point, OperLog operLog) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行方法
        Object result = point.proceed();

        // 计算执行时间
        long executionTime = System.currentTimeMillis() - startTime;

        try {
            // 记录操作日志
            recordLog(point, operLog, result, executionTime);
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }

        return result;
    }

    private void recordLog(ProceedingJoinPoint point, OperLog operLog, Object result, long executionTime) {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();

        // 构建日志信息
        StringBuilder logInfo = new StringBuilder();
        logInfo.append("操作模块: ").append(operLog.title()).append(" | ");
        logInfo.append("操作类型: ").append(operLog.businessType().getInfo()).append(" | ");
        logInfo.append("请求方法: ").append(point.getTarget().getClass().getName()).append(".").append(point.getSignature().getName()).append(" | ");
        logInfo.append("请求方式: ").append(request.getMethod()).append(" | ");
        logInfo.append("请求URL: ").append(request.getRequestURI()).append(" | ");
        logInfo.append("执行时间: ").append(executionTime).append("ms");

        // 获取操作人
        try {
            String username = SecurityUtils.getUsername();
            logInfo.append(" | 操作人: ").append(username);
        } catch (Exception e) {
            log.debug("获取操作人失败");
        }

        // 保存请求参数
        if (operLog.isSaveRequestData()) {
            try {
                String params = objectMapper.writeValueAsString(point.getArgs());
                logInfo.append(" | 请求参数: ").append(params);
            } catch (Exception e) {
                log.debug("序列化请求参数失败");
            }
        }

        // 保存响应数据
        if (operLog.isSaveResponseData() && result != null) {
            try {
                String response = objectMapper.writeValueAsString(result);
                logInfo.append(" | 响应数据: ").append(response);
            } catch (Exception e) {
                log.debug("序列化响应数据失败");
            }
        }

        log.info("操作日志: {}", logInfo);
    }
}
