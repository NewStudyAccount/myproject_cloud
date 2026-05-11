package com.project.cloud.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.cloud.generator.domain.entity.GenDatasource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 */
@Mapper
public interface GenDatasourceMapper extends BaseMapper<GenDatasource> {
}
