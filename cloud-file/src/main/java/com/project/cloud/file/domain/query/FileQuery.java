package com.project.cloud.file.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileQuery extends BaseQuery {

    private String fileName;
    private String fileType;
    private String storageType;
}
