package com.project.cloud.api.generator.fallback;

import com.project.cloud.api.generator.RemoteGeneratorService;
import com.project.cloud.api.generator.domain.RemoteGenResult;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteGeneratorFallback implements FallbackFactory<RemoteGeneratorService> {

    @Override
    public RemoteGeneratorService create(Throwable cause) {
        log.error("代码生成服务调用失败", cause);
        return new RemoteGeneratorService() {
            @Override
            public Result<RemoteGenResult> previewCode(Long configId) {
                log.error("预览代码失败", cause);
                return Result.error("预览代码失败");
            }

            @Override
            public byte[] downloadCode(Long configId) {
                log.error("下载代码失败", cause);
                return new byte[0];
            }
        };
    }
}