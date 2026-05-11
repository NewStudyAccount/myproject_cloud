package com.project.cloud.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据 DTO
 */
@Data
@Schema(description = "字典数据DTO")
public class SysDictDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典数据ID")
    private Long id;

    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型")
    private String dictType;

    @NotBlank(message = "字典标签不能为空")
    @Schema(description = "字典标签")
    private String dictLabel;

    @NotBlank(message = "字典值不能为空")
    @Schema(description = "字典值")
    private String dictValue;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态")
    private Integer status;
}
