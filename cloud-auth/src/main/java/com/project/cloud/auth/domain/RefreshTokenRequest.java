package com.project.cloud.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshTokenRequest implements Serializable {

    @NotBlank(message = "刷新Token不能为空")
    private String refreshToken;
}