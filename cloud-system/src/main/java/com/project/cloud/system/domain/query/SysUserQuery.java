package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQuery extends BaseQuery {

    private String username;
    private String nickname;
    private String phone;
    private Integer status;
}
