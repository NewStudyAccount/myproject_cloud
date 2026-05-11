package com.project.cloud.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型 VO
 */
@Data
@Schema(description = "字典类型信息")
public class SysDictTypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型ID")
    private Long id;

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
