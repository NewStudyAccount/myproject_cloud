package com.project.cloud.api.file;

import com.project.cloud.api.file.fallback.RemoteFileFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件服务 Feign 接口
 */
@FeignClient(value = "cloud-file", fallbackFactory = RemoteFileFallback.class)
public interface RemoteFileService {

    /**
     * 上传文件
     */
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<Map<String, Object>> upload(@RequestPart("file") MultipartFile file);

    /**
     * 删除文件
     */
    @PostMapping("/file/delete")
    Result<Void> delete(@RequestBody Map<String, String> params);

    /**
     * 获取文件信息
     */
    @PostMapping("/file/detail")
    Result<Map<String, Object>> getFileInfo(@RequestBody Map<String, String> params);
}
