package com.project.cloud.api.system.fallback;

import com.project.cloud.api.system.RemoteUserService;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户服务降级处理
 */
@Slf4j
@Component
public class RemoteUserFallback implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("用户服务调用失败: {}", cause.getMessage());
        return new RemoteUserService() {
            @Override
            public Result<Map<String, Object>> getUserByUsername(Map<String, String> params) {
                return Result.error("获取用户失败: " + cause.getMessage());
            }

            @Override
            public Result<Void> registerUser(Map<String, Object> params) {
                return Result.error("注册用户失败: " + cause.getMessage());
            }
        };
    }
}
