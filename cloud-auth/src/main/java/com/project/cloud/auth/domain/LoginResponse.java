package com.project.cloud.auth.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String tokenType;
}
