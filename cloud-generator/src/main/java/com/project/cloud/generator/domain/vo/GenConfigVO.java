package com.project.cloud.generator.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GenConfigVO implements Serializable {

    private Long id;
    private String tableName;
    private String tableComment;
    private String moduleName;
    private String functionName;
    private String packageName;
    private String author;
    private Integer status;
    private LocalDateTime createTime;
}
