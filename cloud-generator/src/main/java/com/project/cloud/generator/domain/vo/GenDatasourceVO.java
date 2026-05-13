package com.project.cloud.generator.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GenDatasourceVO implements Serializable {

    private Long id;
    private String name;
    private String url;
    private String username;
    private String dbType;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
}
