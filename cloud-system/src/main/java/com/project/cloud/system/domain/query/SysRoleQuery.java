package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleQuery extends BaseQuery {

    private String roleName;
    private String roleKey;
    private Integer status;
}
