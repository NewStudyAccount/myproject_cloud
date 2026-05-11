package com.project.cloud.file.service.impl;

import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.service.IFileStorageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO 存储服务实现
 */
@Slf4j
@Service
public class MinioStorageService implements IFileStorageService {

    @Override
    public Map<String, Object> upload(MultipartFile file, FileStorageConfig config) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(config.getEndpoint())
                    .credentials(config.getAccessKey(), config.getSecretKey())
                    .build();

            String bucketName = config.getBucketName();

            // 检查存储桶是否存在
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());

            if (!exists) {
                // 创建存储桶
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            // 获取文件 URL
            String url;
            if (config.getDomain() != null && !config.getDomain().isEmpty()) {
                url = config.getDomain() + "/" + bucketName + "/" + fileName;
            } else {
                url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .method(Method.GET)
                        .expiry(7, TimeUnit.DAYS)
                        .build());
            }

            Map<String, Object> result = new HashMap<>();
            result.put("fileName", fileName);
            result.put("originalName", originalFilename);
            result.put("filePath", fileName);
            result.put("url", url);
            result.put("size", file.getSize());
            result.put("type", file.getContentType());

            return result;
        } catch (Exception e) {
            log.error("MinIO 上传文件失败", e);
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }

    @Override
    public void delete(String filePath, FileStorageConfig config) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(config.getEndpoint())
                    .credentials(config.getAccessKey(), config.getSecretKey())
                    .build();

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            log.error("MinIO 删除文件失败", e);
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String filePath, FileStorageConfig config) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(config.getEndpoint())
                    .credentials(config.getAccessKey(), config.getSecretKey())
                    .build();

            if (config.getDomain() != null && !config.getDomain().isEmpty()) {
                return config.getDomain() + "/" + config.getBucketName() + "/" + filePath;
            }

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(config.getBucketName())
                    .object(filePath)
                    .method(Method.GET)
                    .expiry(7, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            log.error("MinIO 获取文件 URL 失败", e);
            throw new RuntimeException("获取文件 URL 失败: " + e.getMessage());
        }
    }
}
