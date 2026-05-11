package com.project.cloud.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.domain.query.GenConfigQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;
import com.project.cloud.generator.mapper.GenConfigMapper;
import com.project.cloud.generator.service.ICodeGeneratorService;
import com.project.cloud.generator.service.IGenConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 代码生成配置服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends BaseService<GenConfigMapper, GenConfig> implements IGenConfigService {

    private final ICodeGeneratorService codeGeneratorService;

    @Override
    public GenConfigVO detail(GenConfigQuery query) {
        if (query.getId() == null) {
            throw new BusinessException("配置ID不能为空");
        }

        GenConfig config = getById(query.getId());
        if (config == null) {
            return null;
        }

        return convertToVO(config);
    }

    @Override
    public PageResult<GenConfigVO> list(GenConfigQuery query) {
        LambdaQueryWrapper<GenConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getTableName()), GenConfig::getTableName, query.getTableName())
                .like(StringUtils.isNotBlank(query.getModuleName()), GenConfig::getModuleName, query.getModuleName())
                .like(StringUtils.isNotBlank(query.getEntityName()), GenConfig::getEntityName, query.getEntityName())
                .orderByDesc(GenConfig::getCreateTime);

        Page<GenConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<GenConfig> result = page(page, wrapper);

        List<GenConfigVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(GenConfigDTO dto) {
        // 检查表名是否存在
        LambdaQueryWrapper<GenConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenConfig::getTableName, dto.getTableName());
        if (count(wrapper) > 0) {
            throw new BusinessException("表名已存在");
        }

        GenConfig config = new GenConfig();
        config.setTableName(dto.getTableName());
        config.setModuleName(dto.getModuleName());
        config.setPackageName(dto.getPackageName());
        config.setEntityName(dto.getEntityName());
        config.setAuthor(dto.getAuthor());
        config.setTplType(dto.getTplType() != null ? dto.getTplType() : 0);

        save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenConfigDTO dto) {
        GenConfig config = getById(dto.getId());
        if (config == null) {
            throw new BusinessException("配置不存在");
        }

        if (StringUtils.isNotBlank(dto.getTableName())) {
            config.setTableName(dto.getTableName());
        }
        if (StringUtils.isNotBlank(dto.getModuleName())) {
            config.setModuleName(dto.getModuleName());
        }
        if (StringUtils.isNotBlank(dto.getPackageName())) {
            config.setPackageName(dto.getPackageName());
        }
        if (StringUtils.isNotBlank(dto.getEntityName())) {
            config.setEntityName(dto.getEntityName());
        }
        if (StringUtils.isNotBlank(dto.getAuthor())) {
            config.setAuthor(dto.getAuthor());
        }
        if (dto.getTplType() != null) {
            config.setTplType(dto.getTplType());
        }

        updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }
        removeByIds(ids);
    }

    @Override
    public Map<String, String> generateCode(Long configId) {
        GenConfig config = getById(configId);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        return codeGeneratorService.generate(config);
    }

    @Override
    public Map<String, String> previewCode(Long configId) {
        GenConfig config = getById(configId);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        return codeGeneratorService.preview(config);
    }

    private GenConfigVO convertToVO(GenConfig config) {
        GenConfigVO vo = new GenConfigVO();
        vo.setId(config.getId());
        vo.setTableName(config.getTableName());
        vo.setModuleName(config.getModuleName());
        vo.setPackageName(config.getPackageName());
        vo.setEntityName(config.getEntityName());
        vo.setAuthor(config.getAuthor());
        vo.setTplType(config.getTplType());
        vo.setCreateTime(config.getCreateTime());
        vo.setUpdateTime(config.getUpdateTime());
        return vo;
    }
}
