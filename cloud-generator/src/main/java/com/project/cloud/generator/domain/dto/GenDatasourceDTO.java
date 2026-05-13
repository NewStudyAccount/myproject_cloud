package com.project.cloud.generator.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenDatasourceDTO implements Serializable {

    private Long id;

    @NotBlank(message = "数据源名称不能为空")
    private String name;

    @NotBlank(message = "连接地址不能为空")
    private String url;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String dbType;

    private Integer status;

    private String remark;
}
