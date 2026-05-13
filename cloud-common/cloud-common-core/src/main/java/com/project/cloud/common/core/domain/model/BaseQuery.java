package com.project.cloud.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseQuery implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String orderByColumn;

    private String isAsc;
}
