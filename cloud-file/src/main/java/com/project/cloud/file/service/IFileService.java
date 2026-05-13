package com.project.cloud.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.file.domain.query.FileQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    FileRecordVO upload(MultipartFile file);

    Page<FileRecordVO> page(FileQuery query);

    void deleteByIds(List<Long> ids);
}
