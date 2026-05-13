package com.project.cloud.api.file;

import com.project.cloud.api.file.domain.RemoteFileInfo;
import com.project.cloud.api.file.fallback.RemoteFileFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "cloud-file", fallbackFactory = RemoteFileFallback.class)
public interface RemoteFileService {

    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<RemoteFileInfo> upload(@RequestParam("file") MultipartFile file);
}