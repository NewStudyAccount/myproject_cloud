package com.project.cloud.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysUserVO implements Serializable {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private String createBy;
    private LocalDateTime createTime;
}
