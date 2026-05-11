package com.project.cloud.api.file.fallback;

import com.project.cloud.api.file.RemoteFileService;
import com.project.cloud.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件服务降级处理
 */
@Slf4j
@Component
public class RemoteFileFallback implements FallbackFactory<RemoteFileService> {

    @Override
    public RemoteFileService create(Throwable cause) {
        log.error("文件服务调用失败: {}", cause.getMessage());
        return new RemoteFileService() {
            @Override
            public Result<Map<String, Object>> upload(MultipartFile file) {
                return Result.error("上传文件失败: " + cause.getMessage());
            }

            @Override
            public Result<Void> delete(Map<String, String> params) {
                return Result.error("删除文件失败: " + cause.getMessage());
            }

            @Override
            public Result<Map<String, Object>> getFileInfo(Map<String, String> params) {
                return Result.error("获取文件信息失败: " + cause.getMessage());
            }
        };
    }
}
