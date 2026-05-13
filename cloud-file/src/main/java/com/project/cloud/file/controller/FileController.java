package com.project.cloud.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.file.domain.query.FileQuery;
import com.project.cloud.file.domain.vo.FileRecordVO;
import com.project.cloud.file.service.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperLog(title = "文件管理", businessType = BusinessType.INSERT)
    public Result<FileRecordVO> upload(@RequestParam("file") MultipartFile file) {
        return Result.success(fileService.upload(file));
    }

    @PostMapping("/list")
    @Operation(summary = "文件列表")
    public Result<PageResult<FileRecordVO>> list(@RequestBody FileQuery query) {
        Page<FileRecordVO> page = fileService.page(query);
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("/delete")
    @Operation(summary = "删除文件")
    @OperLog(title = "文件管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        fileService.deleteByIds(ids);
        return Result.success();
    }
}
