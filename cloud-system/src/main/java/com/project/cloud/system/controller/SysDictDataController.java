package com.project.cloud.system.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.query.SysDictDataQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.service.ISysDictDataService;
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
 * 字典数据控制器
 */
@Tag(name = "字典数据管理")
@RestController
@RequestMapping("/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final ISysDictDataService dictDataService;

    @PostMapping("/detail")
    @Operation(summary = "查询字典数据详情")
    public Result<SysDictDataVO> detail(@RequestBody SysDictDataQuery query) {
        return Result.success(dictDataService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询字典数据列表")
    public Result<PageResult<SysDictDataVO>> list(@RequestBody SysDictDataQuery query) {
        return Result.success(dictDataService.list(query));
    }

    @PostMapping("/listByDictType")
    @Operation(summary = "根据字典类型查询字典数据")
    public Result<List<SysDictDataVO>> listByDictType(@RequestBody String dictType) {
        return Result.success(dictDataService.listByDictType(dictType));
    }

    @PostMapping("/add")
    @Operation(summary = "新增字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid SysDictDataDTO dto) {
        dictDataService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid SysDictDataDTO dto) {
        dictDataService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        dictDataService.delete(ids);
        return Result.success();
    }
}
