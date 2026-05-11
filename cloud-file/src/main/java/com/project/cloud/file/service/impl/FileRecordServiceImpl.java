package com.project.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.file.domain.entity.FileRecord;
import com.project.cloud.file.domain.entity.FileStorageConfig;
import com.project.cloud.file.domain.query.FileRecordQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import com.project.cloud.file.mapper.FileRecordMapper;
import com.project.cloud.file.service.IFileRecordService;
import com.project.cloud.file.service.IFileStorageConfigService;
import com.project.cloud.file.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 文件记录服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileRecordServiceImpl extends BaseService<FileRecordMapper, FileRecord> implements IFileRecordService {

    private final IFileStorageConfigService storageConfigService;
    private final IFileStorageService fileStorageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 获取存储配置
        FileStorageConfig config = storageConfigService.getDefaultConfig();
        if (config == null) {
            throw new BusinessException("未配置默认存储");
        }

        // 上传文件
        Map<String, Object> uploadResult = fileStorageService.upload(file, config);

        // 保存文件记录
        FileRecord record = new FileRecord();
        record.setFileName((String) uploadResult.get("fileName"));
        record.setOriginalName(file.getOriginalFilename());
        record.setFilePath((String) uploadResult.get("filePath"));
        record.setFileUrl((String) uploadResult.get("url"));
        record.setFileSize(file.getSize());
        record.setFileType(file.getContentType());
        record.setStorageType(config.getStorageType());
        record.setStorageConfigId(config.getId());
        record.setCreateBy("system");
        record.setCreateTime(LocalDateTime.now());
        record.setDeleted(0);

        save(record);

        return uploadResult;
    }

    @Override
    public FileRecordVO detail(FileRecordQuery query) {
        if (query.getId() == null) {
            throw new BusinessException("文件ID不能为空");
        }

        FileRecord record = getById(query.getId());
        if (record == null) {
            return null;
        }

        return convertToVO(record);
    }

    @Override
    public PageResult<FileRecordVO> list(FileRecordQuery query) {
        LambdaQueryWrapper<FileRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getFileName()), FileRecord::getFileName, query.getFileName())
                .like(StringUtils.isNotBlank(query.getOriginalName()), FileRecord::getOriginalName, query.getOriginalName())
                .eq(StringUtils.isNotBlank(query.getFileType()), FileRecord::getFileType, query.getFileType())
                .eq(StringUtils.isNotBlank(query.getStorageType()), FileRecord::getStorageType, query.getStorageType())
                .orderByDesc(FileRecord::getCreateTime);

        Page<FileRecord> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<FileRecord> result = page(page, wrapper);

        List<FileRecordVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }

        // 删除文件
        for (Long id : ids) {
            FileRecord record = getById(id);
            if (record != null) {
                try {
                    FileStorageConfig config = storageConfigService.getById(record.getStorageConfigId());
                    if (config != null) {
                        fileStorageService.delete(record.getFilePath(), config);
                    }
                } catch (Exception e) {
                    log.error("删除文件失败: {}", record.getFilePath(), e);
                }
            }
        }

        removeByIds(ids);
    }

    private FileRecordVO convertToVO(FileRecord record) {
        FileRecordVO vo = new FileRecordVO();
        vo.setId(record.getId());
        vo.setFileName(record.getFileName());
        vo.setOriginalName(record.getOriginalName());
        vo.setFilePath(record.getFilePath());
        vo.setFileUrl(record.getFileUrl());
        vo.setFileSize(record.getFileSize());
        vo.setFileType(record.getFileType());
        vo.setStorageType(record.getStorageType());
        vo.setCreateTime(record.getCreateTime());
        return vo;
    }
}
