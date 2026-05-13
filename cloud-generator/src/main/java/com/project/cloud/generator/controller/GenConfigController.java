package com.project.cloud.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;
import com.project.cloud.generator.service.IGenConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "代码生成配置")
@RestController
@RequestMapping("/gen")
@RequiredArgsConstructor
public class GenConfigController {

    private final IGenConfigService genConfigService;

    @PostMapping("/list")
    @Operation(summary = "配置列表")
    public Result<PageResult<GenConfigVO>> list(@RequestBody GenQuery query) {
        Page<GenConfigVO> page = genConfigService.page(query);
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("/add")
    @Operation(summary = "新增配置")
    @OperLog(title = "代码生成配置", businessType = BusinessType.INSERT)
    public Result<Void> add(@Valid @RequestBody GenConfigDTO dto) {
        genConfigService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改配置")
    @OperLog(title = "代码生成配置", businessType = BusinessType.UPDATE)
    public Result<Void> update(@Valid @RequestBody GenConfigDTO dto) {
        genConfigService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除配置")
    @OperLog(title = "代码生成配置", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        genConfigService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping("/preview")
    @Operation(summary = "预览代码")
    public Result<Map<String, String>> preview(@RequestBody Long configId) {
        return Result.success(genConfigService.previewCode(configId));
    }

    @PostMapping("/download")
    @Operation(summary = "下载代码")
    public ResponseEntity<byte[]> download(@RequestBody Long configId) {
        byte[] data = genConfigService.downloadCode(configId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=code.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}
