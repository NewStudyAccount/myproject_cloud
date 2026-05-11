package com.project.cloud.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.cloud.file.domain.entity.FileStorageConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件存储配置 Mapper
 */
@Mapper
public interface FileStorageConfigMapper extends BaseMapper<FileStorageConfig> {
}
