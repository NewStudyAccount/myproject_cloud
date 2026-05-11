package com.project.cloud.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.file.domain.entity.FileRecord;
import com.project.cloud.file.domain.query.FileRecordQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件记录服务接口
 */
public interface IFileRecordService extends IService<FileRecord> {

    /**
     * 上传文件
     */
    Map<String, Object> upload(MultipartFile file);

    /**
     * 查询文件详情
     */
    FileRecordVO detail(FileRecordQuery query);

    /**
     * 查询文件列表（分页）
     */
    PageResult<FileRecordVO> list(FileRecordQuery query);

    /**
     * 删除文件
     */
    void delete(List<Long> ids);
}
