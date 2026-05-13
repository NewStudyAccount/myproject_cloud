package com.project.cloud.api.system.fallback;

import com.project.cloud.api.system.RemoteUserService;
import com.project.cloud.api.system.domain.RemoteUserInfo;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserFallback implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("用户服务调用失败", cause);
        return new RemoteUserService() {
            @Override
            public Result<RemoteUserInfo> getUserByUsername(String username) {
                log.error("获取用户信息失败: {}", username, cause);
                return Result.error("获取用户信息失败");
            }
        };
    }
}