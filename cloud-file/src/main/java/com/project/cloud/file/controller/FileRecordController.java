package com.project.cloud.file.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.file.domain.query.FileRecordQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import com.project.cloud.file.service.IFileRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件记录控制器
 */
@Tag(name = "文件记录管理")
@RestController
@RequestMapping("/file/record")
@RequiredArgsConstructor
public class FileRecordController {

    private final IFileRecordService fileRecordService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperLog(title = "文件上传", businessType = BusinessType.IMPORT)
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        return Result.success(fileRecordService.upload(file));
    }

    @PostMapping("/detail")
    @Operation(summary = "查询文件详情")
    public Result<FileRecordVO> detail(@RequestBody FileRecordQuery query) {
        return Result.success(fileRecordService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询文件列表")
    public Result<PageResult<FileRecordVO>> list(@RequestBody FileRecordQuery query) {
        return Result.success(fileRecordService.list(query));
    }

    @PostMapping("/delete")
    @Operation(summary = "删除文件")
    @OperLog(title = "文件删除", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        fileRecordService.delete(ids);
        return Result.success();
    }
}
