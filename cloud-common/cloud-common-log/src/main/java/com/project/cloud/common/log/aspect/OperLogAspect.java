package com.project.cloud.common.log.aspect;

import com.project.cloud.common.core.utils.JsonUtils;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.common.log.event.OperLogEvent;
import com.project.cloud.common.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperLogAspect {

    private final ApplicationEventPublisher publisher;

    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint point, OperLog operLog) throws Throwable {
        long startTime = System.currentTimeMillis();

        OperLogEvent event = new OperLogEvent();
        event.setTitle(operLog.title());
        event.setBusinessType(operLog.businessType().ordinal());
        event.setMethod(point.getTarget().getClass().getName() + "." + point.getSignature().getName());
        event.setOperTime(LocalDateTime.now());

        fillOperatorInfo(event);

        if (operLog.isSaveRequestData()) {
            try {
                Object[] args = point.getArgs();
                Map<String, Object> params = new HashMap<>();
                for (int i = 0; i < args.length; i++) {
                    if (!(args[i] instanceof MultipartFile)) {
                        params.put("arg" + i, args[i]);
                    }
                }
                event.setRequestParam(JsonUtils.toJson(params));
            } catch (Exception e) {
                log.warn("记录请求参数失败", e);
            }
        }

        Object result;
        try {
            result = point.proceed();
            event.setStatus(0);
            event.setErrorMsg(null);
        } catch (Exception e) {
            event.setStatus(1);
            event.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            event.setCostTime(costTime);

            if (operLog.isSaveResponseData() && event.getStatus() == 0) {
                try {
                    event.setJsonResult(JsonUtils.toJson(result));
                } catch (Exception e) {
                    log.warn("记录响应数据失败", e);
                }
            }

            publisher.publishEvent(event);
        }

        return result;
    }

    private void fillOperatorInfo(OperLogEvent event) {
        try {
            event.setOperName(SecurityUtils.getUsername());
        } catch (Exception e) {
            log.debug("获取操作者用户名失败", e);
            event.setOperName("unknown");
        }

        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String ip = request.getHeader("X-Forwarded-For");
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
                if (ip != null && ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }
                event.setOperIp(ip);
            }
        } catch (Exception e) {
            log.debug("获取操作者IP失败", e);
        }
    }
}