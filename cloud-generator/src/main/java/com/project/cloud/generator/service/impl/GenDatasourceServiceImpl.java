package com.project.cloud.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.generator.convert.GenConvert;
import com.project.cloud.generator.domain.dto.GenDatasourceDTO;
import com.project.cloud.generator.domain.entity.GenDatasource;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenDatasourceVO;
import com.project.cloud.generator.mapper.GenDatasourceMapper;
import com.project.cloud.generator.service.IGenDatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GenDatasourceServiceImpl extends BaseService<GenDatasourceMapper, GenDatasource> implements IGenDatasourceService {

    @Override
    public Page<GenDatasourceVO> page(GenQuery query) {
        Page<GenDatasource> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<GenDatasource> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(GenDatasource::getCreateTime);

        Page<GenDatasource> result = baseMapper.selectPage(page, wrapper);
        return GenConvert.INSTANCE.toDatasourcePageVO(result);
    }

    @Override
    public void add(GenDatasourceDTO dto) {
        GenDatasource entity = GenConvert.INSTANCE.toDatasourceEntity(dto);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        save(entity);
    }

    @Override
    public void update(GenDatasourceDTO dto) {
        GenDatasource entity = GenConvert.INSTANCE.toDatasourceEntity(dto);
        updateById(entity);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public List<String> getTableNames(Long datasourceId) {
        GenDatasource ds = getById(datasourceId);
        if (ds == null) {
            return List.of();
        }

        List<String> tables = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword())) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(conn.getCatalog(), conn.getSchema(), "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            log.error("获取表名失败", e);
        }
        return tables;
    }
}
