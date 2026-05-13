package com.project.cloud.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.generator.domain.dto.GenDatasourceDTO;
import com.project.cloud.generator.domain.entity.GenDatasource;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenDatasourceVO;

import java.util.List;

public interface IGenDatasourceService extends IService<GenDatasource> {

    Page<GenDatasourceVO> page(GenQuery query);

    void add(GenDatasourceDTO dto);

    void update(GenDatasourceDTO dto);

    void deleteByIds(List<Long> ids);

    List<String> getTableNames(Long datasourceId);
}
