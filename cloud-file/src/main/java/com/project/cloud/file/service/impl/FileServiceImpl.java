package com.project.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.file.domain.entity.FileRecord;
import com.project.cloud.file.domain.query.FileQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import com.project.cloud.file.mapper.FileRecordMapper;
import com.project.cloud.file.service.IFileService;
import com.project.cloud.file.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends BaseService<FileRecordMapper, FileRecord> implements IFileService {

    private final IFileStorageService storageService;

    @Override
    public FileRecordVO upload(MultipartFile file) {
        String path = "upload/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String filePath = storageService.upload(file, path);

        FileRecord record = new FileRecord();
        record.setFileName(file.getOriginalFilename());
        record.setOriginalName(file.getOriginalFilename());
        record.setFilePath(filePath);
        record.setFileUrl(storageService.getFileUrl(filePath));
        record.setFileSize(file.getSize());
        record.setFileType(file.getContentType());
        record.setStorageType("minio");

        save(record);

        FileRecordVO vo = new FileRecordVO();
        vo.setId(record.getId());
        vo.setFileName(record.getFileName());
        vo.setFileUrl(record.getFileUrl());
        vo.setFileSize(record.getFileSize());
        vo.setFileType(record.getFileType());
        return vo;
    }

    @Override
    public Page<FileRecordVO> page(FileQuery query) {
        Page<FileRecord> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<FileRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getFileName() != null, FileRecord::getFileName, query.getFileName())
                .like(query.getFileType() != null, FileRecord::getFileType, query.getFileType())
                .eq(query.getStorageType() != null, FileRecord::getStorageType, query.getStorageType())
                .orderByDesc(FileRecord::getCreateTime);

        Page<FileRecord> result = baseMapper.selectPage(page, wrapper);

        Page<FileRecordVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(result.getRecords().stream().map(this::toVO).toList());
        return voPage;
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        List<FileRecord> records = listByIds(ids);
        for (FileRecord record : records) {
            storageService.delete(record.getFilePath());
        }
        removeByIds(ids);
    }

    private FileRecordVO toVO(FileRecord record) {
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
