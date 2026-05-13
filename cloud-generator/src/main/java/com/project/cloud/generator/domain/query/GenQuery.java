package com.project.cloud.generator.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenQuery extends BaseQuery {

    private String tableName;
    private String tableComment;
}
