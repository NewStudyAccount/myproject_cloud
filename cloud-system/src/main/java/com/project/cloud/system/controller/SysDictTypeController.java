package com.project.cloud.system.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.query.SysDictTypeQuery;
import com.project.cloud.system.domain.vo.SysDictTypeVO;
import com.project.cloud.system.service.ISysDictTypeService;
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
 * 字典类型控制器
 */
@Tag(name = "字典类型管理")
@RestController
@RequestMapping("/dict/type")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final ISysDictTypeService dictTypeService;

    @PostMapping("/detail")
    @Operation(summary = "查询字典类型详情")
    public Result<SysDictTypeVO> detail(@RequestBody SysDictTypeQuery query) {
        return Result.success(dictTypeService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询字典类型列表")
    public Result<PageResult<SysDictTypeVO>> list(@RequestBody SysDictTypeQuery query) {
        return Result.success(dictTypeService.list(query));
    }

    @PostMapping("/listAll")
    @Operation(summary = "查询所有字典类型")
    public Result<List<SysDictTypeVO>> listAll() {
        return Result.success(dictTypeService.listAll());
    }

    @PostMapping("/add")
    @Operation(summary = "新增字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid SysDictTypeDTO dto) {
        dictTypeService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid SysDictTypeDTO dto) {
        dictTypeService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        dictTypeService.delete(ids);
        return Result.success();
    }
}
