package com.project.cloud.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典类型 DTO
 */
@Data
@Schema(description = "字典类型DTO")
public class SysDictTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型ID")
    private Long id;

    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称")
    private String dictName;

    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态")
    private Integer status;
}
