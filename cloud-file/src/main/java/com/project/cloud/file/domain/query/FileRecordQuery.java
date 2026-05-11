package com.project.cloud.file.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件记录查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件记录查询参数")
public class FileRecordQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "原始文件名")
    private String originalName;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "存储类型")
    private String storageType;
}
