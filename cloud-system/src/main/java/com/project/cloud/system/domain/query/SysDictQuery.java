package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictQuery extends BaseQuery {

    private String dictName;
    private String dictType;
    private Integer status;
}
