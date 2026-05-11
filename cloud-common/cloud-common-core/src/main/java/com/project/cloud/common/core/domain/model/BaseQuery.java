package com.project.cloud.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数基类
 */
@Data
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderByColumn;

    /**
     * 排序方式（asc/desc）
     */
    private String isAsc;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;
}
