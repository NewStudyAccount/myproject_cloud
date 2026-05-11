package com.project.cloud.file.controller;

import com.project.cloud.common.core.result.Result;
import com.project.cloud.file.service.IFileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件管理控制器
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final IFileStorageService fileStorageService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        return Result.success(fileStorageService.upload(file));
    }

    @PostMapping("/delete")
    @Operation(summary = "删除文件")
    public Result<Void> delete(@RequestBody Map<String, String> params) {
        String filePath = params.get("filePath");
        fileStorageService.delete(filePath);
        return Result.success();
    }

    @PostMapping("/detail")
    @Operation(summary = "获取文件信息")
    public Result<Map<String, Object>> getFileInfo(@RequestBody Map<String, String> params) {
        String filePath = params.get("filePath");
        String url = fileStorageService.getFileUrl(filePath);
        return Result.success(Map.of("url", url));
    }
}
