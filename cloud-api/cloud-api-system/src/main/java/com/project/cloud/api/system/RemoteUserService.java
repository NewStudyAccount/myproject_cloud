package com.project.cloud.api.system;

import com.project.cloud.api.system.fallback.RemoteUserFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 用户服务 Feign 接口
 */
@FeignClient(value = "cloud-system", fallbackFactory = RemoteUserFallback.class)
public interface RemoteUserService {

    /**
     * 根据用户名查询用户
     */
    @PostMapping("/user/detail")
    Result<Map<String, Object>> getUserByUsername(@RequestBody Map<String, String> params);

    /**
     * 注册用户
     */
    @PostMapping("/user/add")
    Result<Void> registerUser(@RequestBody Map<String, Object> params);
}
