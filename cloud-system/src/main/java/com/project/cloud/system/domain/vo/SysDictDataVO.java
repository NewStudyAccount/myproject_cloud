package com.project.cloud.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDictDataVO implements Serializable {

    private Long id;
    private String dictType;
    private String dictLabel;
    private String dictValue;
    private Integer sort;
    private Integer status;
    private String remark;
}
