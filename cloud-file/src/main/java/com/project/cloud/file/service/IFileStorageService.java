package com.project.cloud.file.service;

import com.project.cloud.file.domain.entity.FileStorageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件存储接口
 */
public interface IFileStorageService {

    /**
     * 上传文件
     */
    Map<String, Object> upload(MultipartFile file, FileStorageConfig config);

    /**
     * 删除文件
     */
    void delete(String filePath, FileStorageConfig config);

    /**
     * 获取文件 URL
     */
    String getFileUrl(String filePath, FileStorageConfig config);
}
