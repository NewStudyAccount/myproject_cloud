package com.project.cloud.api.file.fallback;

import com.project.cloud.api.file.RemoteFileService;
import com.project.cloud.api.file.domain.RemoteFileInfo;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class RemoteFileFallback implements FallbackFactory<RemoteFileService> {

    @Override
    public RemoteFileService create(Throwable cause) {
        log.error("文件服务调用失败", cause);
        return new RemoteFileService() {
            @Override
            public Result<RemoteFileInfo> upload(MultipartFile file) {
                log.error("文件上传失败", cause);
                return Result.error("文件上传失败");
            }
        };
    }
}