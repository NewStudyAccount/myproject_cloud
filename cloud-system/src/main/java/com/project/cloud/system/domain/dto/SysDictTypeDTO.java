package com.project.cloud.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDictTypeDTO implements Serializable {

    private Long id;

    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    private Integer status;

    private String remark;
}
