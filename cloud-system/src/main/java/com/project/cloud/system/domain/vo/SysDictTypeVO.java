package com.project.cloud.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysDictTypeVO implements Serializable {

    private Long id;
    private String dictName;
    private String dictType;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
}
