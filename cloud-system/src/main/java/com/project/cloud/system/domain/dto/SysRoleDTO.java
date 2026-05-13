package com.project.cloud.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleDTO implements Serializable {

    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @NotBlank(message = "角色标识不能为空")
    private String roleKey;

    private Integer sort;

    private Integer status;
}
