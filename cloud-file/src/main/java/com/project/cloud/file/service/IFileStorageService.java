package com.project.cloud.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    String upload(MultipartFile file, String path);

    void delete(String filePath);

    String getFileUrl(String filePath);
}
