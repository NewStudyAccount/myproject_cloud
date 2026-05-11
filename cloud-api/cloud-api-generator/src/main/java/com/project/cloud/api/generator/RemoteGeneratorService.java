package com.project.cloud.api.generator;

import com.project.cloud.api.generator.fallback.RemoteGeneratorFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 代码生成服务 Feign 接口
 */
@FeignClient(value = "cloud-generator", fallbackFactory = RemoteGeneratorFallback.class)
public interface RemoteGeneratorService {

    /**
     * 生成代码
     */
    @PostMapping("/generator/generate")
    Result<Map<String, String>> generateCode(@RequestBody Map<String, Object> params);

    /**
     * 预览代码
     */
    @PostMapping("/generator/preview")
    Result<Map<String, String>> previewCode(@RequestBody Map<String, Object> params);
}
