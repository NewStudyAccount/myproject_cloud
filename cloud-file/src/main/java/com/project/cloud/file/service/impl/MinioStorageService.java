package com.project.cloud.file.service.impl;

import com.project.cloud.file.service.IFileStorageService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class MinioStorageService implements IFileStorageService {

    @Value("${storage.minio.endpoint}")
    private String endpoint;

    @Value("${storage.minio.access-key}")
    private String accessKey;

    @Value("${storage.minio.secret-key}")
    private String secretKey;

    @Value("${storage.minio.bucket-name}")
    private String bucketName;

    @Override
    public String upload(MultipartFile file, String path) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查bucket是否存在
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String fileName = path + "/" + UUID.randomUUID() + getExtension(file.getOriginalFilename());

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return fileName;
        } catch (Exception e) {
            log.error("MinIO上传失败", e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void delete(String filePath) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            log.error("MinIO删除失败", e);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        return endpoint + "/" + bucketName + "/" + filePath;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
