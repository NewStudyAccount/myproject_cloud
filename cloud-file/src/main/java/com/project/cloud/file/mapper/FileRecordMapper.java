package com.project.cloud.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.cloud.file.domain.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件记录 Mapper
 */
@Mapper
public interface FileRecordMapper extends BaseMapper<FileRecord> {
}
