package com.project.cloud.file.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.file.domain.dto.FileStorageConfigDTO;
import com.project.cloud.file.domain.query.FileStorageConfigQuery;
import com.project.cloud.file.domain.vo.FileStorageConfigVO;
import com.project.cloud.file.service.IFileStorageConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件存储配置控制器
 */
@Tag(name = "文件存储配置管理")
@RestController
@RequestMapping("/file/config")
@RequiredArgsConstructor
public class FileStorageConfigController {

    private final IFileStorageConfigService storageConfigService;

    @PostMapping("/detail")
    @Operation(summary = "查询配置详情")
    public Result<FileStorageConfigVO> detail(@RequestBody FileStorageConfigQuery query) {
        return Result.success(storageConfigService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询配置列表")
    public Result<PageResult<FileStorageConfigVO>> list(@RequestBody FileStorageConfigQuery query) {
        return Result.success(storageConfigService.list(query));
    }

    @PostMapping("/listAll")
    @Operation(summary = "查询所有配置")
    public Result<List<FileStorageConfigVO>> listAll() {
        return Result.success(storageConfigService.listAll());
    }

    @PostMapping("/add")
    @Operation(summary = "新增配置")
    @OperLog(title = "文件存储配置管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid FileStorageConfigDTO dto) {
        storageConfigService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新配置")
    @OperLog(title = "文件存储配置管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid FileStorageConfigDTO dto) {
        storageConfigService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除配置")
    @OperLog(title = "文件存储配置管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        storageConfigService.delete(ids);
        return Result.success();
    }
}
