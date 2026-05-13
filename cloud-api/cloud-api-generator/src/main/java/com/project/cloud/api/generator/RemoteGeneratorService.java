package com.project.cloud.api.generator;

import com.project.cloud.api.generator.domain.RemoteGenResult;
import com.project.cloud.api.generator.fallback.RemoteGeneratorFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cloud-generator", fallbackFactory = RemoteGeneratorFallback.class)
public interface RemoteGeneratorService {

    @PostMapping("/gen/preview")
    Result<RemoteGenResult> previewCode(@RequestBody Long configId);

    @PostMapping("/gen/download")
    byte[] downloadCode(@RequestBody Long configId);
}