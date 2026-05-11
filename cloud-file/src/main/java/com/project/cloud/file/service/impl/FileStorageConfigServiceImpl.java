package com.project.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.file.domain.dto.FileStorageConfigDTO;
import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.domain.query.FileStorageConfigQuery;
import com.project.cloud.file.domain.vo.FileStorageConfigVO;
import com.project.cloud.file.mapper.FileStorageConfigMapper;
import com.project.cloud.file.service.IFileStorageConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件存储配置服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageConfigServiceImpl extends BaseService<FileStorageConfigMapper, FileStorageConfig> implements IFileStorageConfigService {

    @Override
    public FileStorageConfigVO detail(FileStorageConfigQuery query) {
        if (query.getId() == null) {
            throw new BusinessException("配置ID不能为空");
        }

        FileStorageConfig config = getById(query.getId());
        if (config == null) {
            return null;
        }

        return convertToVO(config);
    }

    @Override
    public PageResult<FileStorageConfigVO> list(FileStorageConfigQuery query) {
        LambdaQueryWrapper<FileStorageConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(query.getStorageType()), FileStorageConfig::getStorageType, query.getStorageType())
                .like(StringUtils.isNotBlank(query.getConfigName()), FileStorageConfig::getConfigName, query.getConfigName())
                .eq(query.getStatus() != null, FileStorageConfig::getStatus, query.getStatus())
                .orderByDesc(FileStorageConfig::getCreateTime);

        Page<FileStorageConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<FileStorageConfig> result = page(page, wrapper);

        List<FileStorageConfigVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    public List<FileStorageConfigVO> listAll() {
        LambdaQueryWrapper<FileStorageConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FileStorageConfig::getStatus, 0)
                .orderByDesc(FileStorageConfig::getIsDefault);

        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FileStorageConfigDTO dto) {
        FileStorageConfig config = new FileStorageConfig();
        config.setStorageType(dto.getStorageType());
        config.setConfigName(dto.getConfigName());
        config.setEndpoint(dto.getEndpoint());
        config.setAccessKey(dto.getAccessKey());
        config.setSecretKey(dto.getSecretKey());
        config.setBucketName(dto.getBucketName());
        config.setDomain(dto.getDomain());
        config.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : 0);
        config.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);

        // 如果设置为默认，取消其他默认
        if (config.getIsDefault() == 1) {
            resetDefault();
        }

        save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileStorageConfigDTO dto) {
        FileStorageConfig config = getById(dto.getId());
        if (config == null) {
            throw new BusinessException("配置不存在");
        }

        if (StringUtils.isNotBlank(dto.getStorageType())) {
            config.setStorageType(dto.getStorageType());
        }
        if (StringUtils.isNotBlank(dto.getConfigName())) {
            config.setConfigName(dto.getConfigName());
        }
        if (StringUtils.isNotBlank(dto.getEndpoint())) {
            config.setEndpoint(dto.getEndpoint());
        }
        if (StringUtils.isNotBlank(dto.getAccessKey())) {
            config.setAccessKey(dto.getAccessKey());
        }
        if (StringUtils.isNotBlank(dto.getSecretKey())) {
            config.setSecretKey(dto.getSecretKey());
        }
        if (StringUtils.isNotBlank(dto.getBucketName())) {
            config.setBucketName(dto.getBucketName());
        }
        if (StringUtils.isNotBlank(dto.getDomain())) {
            config.setDomain(dto.getDomain());
        }
        if (dto.getIsDefault() != null) {
            config.setIsDefault(dto.getIsDefault());
            if (dto.getIsDefault() == 1) {
                resetDefault();
            }
        }
        if (dto.getStatus() != null) {
            config.setStatus(dto.getStatus());
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
    public FileStorageConfig getDefaultConfig() {
        LambdaQueryWrapper<FileStorageConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FileStorageConfig::getIsDefault, 1)
                .eq(FileStorageConfig::getStatus, 0);
        return getOne(wrapper);
    }

    private void resetDefault() {
        LambdaQueryWrapper<FileStorageConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FileStorageConfig::getIsDefault, 1);
        FileStorageConfig config = new FileStorageConfig();
        config.setIsDefault(0);
        update(config, wrapper);
    }

    private FileStorageConfigVO convertToVO(FileStorageConfig config) {
        FileStorageConfigVO vo = new FileStorageConfigVO();
        vo.setId(config.getId());
        vo.setStorageType(config.getStorageType());
        vo.setConfigName(config.getConfigName());
        vo.setEndpoint(config.getEndpoint());
        vo.setAccessKey(config.getAccessKey());
        vo.setBucketName(config.getBucketName());
        vo.setDomain(config.getDomain());
        vo.setIsDefault(config.getIsDefault());
        vo.setStatus(config.getStatus());
        vo.setCreateTime(config.getCreateTime());
        vo.setUpdateTime(config.getUpdateTime());
        return vo;
    }
}
