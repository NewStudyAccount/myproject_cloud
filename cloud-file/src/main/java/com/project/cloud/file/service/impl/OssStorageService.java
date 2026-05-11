package com.project.cloud.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.service.IFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 阿里云 OSS 存储服务实现
 */
@Slf4j
@Service
public class OssStorageService implements IFileStorageService {

    @Override
    public Map<String, Object> upload(MultipartFile file, FileStorageConfig config) {
        try {
            OSS ossClient = createOssClient(config);

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

            // 上传文件
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(config.getBucketName(), fileName, inputStream);
            inputStream.close();

            // 获取文件 URL
            String url;
            if (config.getDomain() != null && !config.getDomain().isEmpty()) {
                url = config.getDomain() + "/" + fileName;
            } else {
                url = "https://" + config.getBucketName() + "." + config.getEndpoint() + "/" + fileName;
            }

            // 关闭 OSS 客户端
            ossClient.shutdown();

            Map<String, Object> result = new HashMap<>();
            result.put("fileName", fileName);
            result.put("originalName", originalFilename);
            result.put("filePath", fileName);
            result.put("url", url);
            result.put("size", file.getSize());
            result.put("type", file.getContentType());

            return result;
        } catch (Exception e) {
            log.error("阿里云 OSS 上传文件失败", e);
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }

    @Override
    public void delete(String filePath, FileStorageConfig config) {
        try {
            OSS ossClient = createOssClient(config);
            ossClient.deleteObject(config.getBucketName(), filePath);
            ossClient.shutdown();
        } catch (Exception e) {
            log.error("阿里云 OSS 删除文件失败", e);
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String filePath, FileStorageConfig config) {
        if (config.getDomain() != null && !config.getDomain().isEmpty()) {
            return config.getDomain() + "/" + filePath;
        }
        return "https://" + config.getBucketName() + "." + config.getEndpoint() + "/" + filePath;
    }

    private OSS createOssClient(FileStorageConfig config) {
        return new OSSClientBuilder().build(
                config.getEndpoint(),
                config.getAccessKey(),
                config.getSecretKey()
        );
    }
}
