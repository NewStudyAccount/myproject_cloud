package com.project.cloud.file.service.impl;

import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.service.IFileStorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 腾讯云 COS 存储服务实现
 */
@Slf4j
@Service
public class CosStorageService implements IFileStorageService {

    @Override
    public Map<String, Object> upload(MultipartFile file, FileStorageConfig config) {
        try {
            COSClient cosClient = createCosClient(config);

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

            // 设置元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // 上传文件
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    config.getBucketName(),
                    fileName,
                    inputStream,
                    metadata
            );
            cosClient.putObject(putObjectRequest);
            inputStream.close();

            // 获取文件 URL
            String url;
            if (config.getDomain() != null && !config.getDomain().isEmpty()) {
                url = config.getDomain() + "/" + fileName;
            } else {
                url = "https://" + config.getBucketName() + ".cos." + config.getEndpoint() + ".myqcloud.com/" + fileName;
            }

            // 关闭 COS 客户端
            cosClient.shutdown();

            Map<String, Object> result = new HashMap<>();
            result.put("fileName", fileName);
            result.put("originalName", originalFilename);
            result.put("filePath", fileName);
            result.put("url", url);
            result.put("size", file.getSize());
            result.put("type", file.getContentType());

            return result;
        } catch (Exception e) {
            log.error("腾讯云 COS 上传文件失败", e);
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }

    @Override
    public void delete(String filePath, FileStorageConfig config) {
        try {
            COSClient cosClient = createCosClient(config);
            cosClient.deleteObject(config.getBucketName(), filePath);
            cosClient.shutdown();
        } catch (Exception e) {
            log.error("腾讯云 COS 删除文件失败", e);
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String filePath, FileStorageConfig config) {
        if (config.getDomain() != null && !config.getDomain().isEmpty()) {
            return config.getDomain() + "/" + filePath;
        }
        return "https://" + config.getBucketName() + ".cos." + config.getEndpoint() + ".myqcloud.com/" + filePath;
    }

    private COSClient createCosClient(FileStorageConfig config) {
        COSCredentials credentials = new BasicCOSCredentials(config.getAccessKey(), config.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(config.getEndpoint()));
        return new COSClient(credentials, clientConfig);
    }
}
