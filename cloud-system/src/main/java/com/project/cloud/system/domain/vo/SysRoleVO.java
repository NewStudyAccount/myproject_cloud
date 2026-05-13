package com.project.cloud.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysRoleVO implements Serializable {

    private Long id;
    private String roleName;
    private String roleKey;
    private Integer sort;
    private Integer status;
    private String createBy;
    private LocalDateTime createTime;
}
