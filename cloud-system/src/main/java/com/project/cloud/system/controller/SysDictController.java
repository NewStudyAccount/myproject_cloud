package com.project.cloud.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.query.SysDictQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.domain.vo.SysDictTypeVO;
import com.project.cloud.system.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "字典管理")
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final ISysDictService dictService;

    @PostMapping("/type/list")
    @Operation(summary = "字典类型列表")
    public Result<PageResult<SysDictTypeVO>> typeList(@RequestBody SysDictQuery query) {
        Page<SysDictTypeVO> page = dictService.pageType(query);
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("/type/add")
    @Operation(summary = "新增字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.INSERT)
    public Result<Void> addType(@Valid @RequestBody SysDictTypeDTO dto) {
        dictService.addType(dto);
        return Result.success();
    }

    @PostMapping("/type/update")
    @Operation(summary = "修改字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.UPDATE)
    public Result<Void> updateType(@Valid @RequestBody SysDictTypeDTO dto) {
        dictService.updateType(dto);
        return Result.success();
    }

    @PostMapping("/type/delete")
    @Operation(summary = "删除字典类型")
    @OperLog(title = "字典类型管理", businessType = BusinessType.DELETE)
    public Result<Void> deleteType(@RequestBody List<Long> ids) {
        dictService.deleteTypeByIds(ids);
        return Result.success();
    }

    @PostMapping("/data/list")
    @Operation(summary = "字典数据列表")
    public Result<List<SysDictDataVO>> dataList(@RequestBody SysDictQuery query) {
        return Result.success(dictService.listData(query.getDictType()));
    }

    @PostMapping("/data/add")
    @Operation(summary = "新增字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.INSERT)
    public Result<Void> addData(@Valid @RequestBody SysDictDataDTO dto) {
        dictService.addData(dto);
        return Result.success();
    }

    @PostMapping("/data/update")
    @Operation(summary = "修改字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.UPDATE)
    public Result<Void> updateData(@Valid @RequestBody SysDictDataDTO dto) {
        dictService.updateData(dto);
        return Result.success();
    }

    @PostMapping("/data/delete")
    @Operation(summary = "删除字典数据")
    @OperLog(title = "字典数据管理", businessType = BusinessType.DELETE)
    public Result<Void> deleteData(@RequestBody List<Long> ids) {
        dictService.deleteDataByIds(ids);
        return Result.success();
    }
}
