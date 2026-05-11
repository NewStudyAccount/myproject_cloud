package com.project.cloud.generator.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.query.GenConfigQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;
import com.project.cloud.generator.service.IGenConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 代码生成配置控制器
 */
@Tag(name = "代码生成配置管理")
@RestController
@RequestMapping("/generator/config")
@RequiredArgsConstructor
public class GenConfigController {

    private final IGenConfigService genConfigService;

    @PostMapping("/detail")
    @Operation(summary = "查询配置详情")
    public Result<GenConfigVO> detail(@RequestBody GenConfigQuery query) {
        return Result.success(genConfigService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询配置列表")
    public Result<PageResult<GenConfigVO>> list(@RequestBody GenConfigQuery query) {
        return Result.success(genConfigService.list(query));
    }

    @PostMapping("/add")
    @Operation(summary = "新增配置")
    @OperLog(title = "代码生成配置管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid GenConfigDTO dto) {
        genConfigService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新配置")
    @OperLog(title = "代码生成配置管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid GenConfigDTO dto) {
        genConfigService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除配置")
    @OperLog(title = "代码生成配置管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        genConfigService.delete(ids);
        return Result.success();
    }

    @PostMapping("/generate")
    @Operation(summary = "生成代码")
    public Result<Map<String, String>> generate(@RequestBody Long configId) {
        return Result.success(genConfigService.generateCode(configId));
    }

    @PostMapping("/preview")
    @Operation(summary = "预览代码")
    public Result<Map<String, String>> preview(@RequestBody Long configId) {
        return Result.success(genConfigService.previewCode(configId));
    }
}
