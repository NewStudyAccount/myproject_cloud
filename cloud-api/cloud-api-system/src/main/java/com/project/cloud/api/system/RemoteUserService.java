package com.project.cloud.api.system;

import com.project.cloud.api.system.domain.RemoteUserInfo;
import com.project.cloud.api.system.fallback.RemoteUserFallback;
import com.project.cloud.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cloud-system", fallbackFactory = RemoteUserFallback.class)
public interface RemoteUserService {

    @PostMapping("/user/getByUsername")
    Result<RemoteUserInfo> getUserByUsername(@RequestBody String username);
}