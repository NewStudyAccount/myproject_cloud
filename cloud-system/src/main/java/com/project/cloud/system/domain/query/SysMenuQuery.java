package com.project.cloud.system.domain.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysMenuQuery implements Serializable {

    private String menuName;
    private Integer status;
}
