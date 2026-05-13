package com.project.cloud.generator.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenConfigDTO implements Serializable {

    private Long id;

    @NotBlank(message = "表名不能为空")
    private String tableName;

    private String tableComment;

    private String moduleName;

    private String functionName;

    private String packageName;

    private String author;
}
