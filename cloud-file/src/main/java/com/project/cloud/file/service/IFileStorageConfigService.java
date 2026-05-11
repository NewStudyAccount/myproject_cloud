package com.project.cloud.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.file.domain.dto.FileStorageConfigDTO;
import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.domain.query.FileStorageConfigQuery;
import com.project.cloud.file.domain.vo.FileStorageConfigVO;

import java.util.List;

/**
 * 文件存储配置服务接口
 */
public interface IFileStorageConfigService extends IService<FileStorageConfig> {

    /**
     * 查询配置详情
     */
    FileStorageConfigVO detail(FileStorageConfigQuery query);

    /**
     * 查询配置列表（分页）
     */
    PageResult<FileStorageConfigVO> list(FileStorageConfigQuery query);

    /**
     * 查询所有配置
     */
    List<FileStorageConfigVO> listAll();

    /**
     * 新增配置
     */
    void add(FileStorageConfigDTO dto);

    /**
     * 更新配置
     */
    void update(FileStorageConfigDTO dto);

    /**
     * 删除配置
     */
    void delete(List<Long> ids);

    /**
     * 获取默认配置
     */
    FileStorageConfig getDefaultConfig();
}
