package com.project.cloud.api.generator.fallback;

import com.project.cloud.api.generator.RemoteGeneratorService;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代码生成服务降级处理
 */
@Slf4j
@Component
public class RemoteGeneratorFallback implements FallbackFactory<RemoteGeneratorService> {

    @Override
    public RemoteGeneratorService create(Throwable cause) {
        log.error("代码生成服务调用失败: {}", cause.getMessage());
        return new RemoteGeneratorService() {
            @Override
            public Result<Map<String, String>> generateCode(Map<String, Object> params) {
                return Result.error("生成代码失败: " + cause.getMessage());
            }

            @Override
            public Result<Map<String, String>> previewCode(Map<String, Object> params) {
                return Result.error("预览代码失败: " + cause.getMessage());
            }
        };
    }
}
