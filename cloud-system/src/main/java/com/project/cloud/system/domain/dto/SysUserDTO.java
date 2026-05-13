package com.project.cloud.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserDTO implements Serializable {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private Integer status;
}
