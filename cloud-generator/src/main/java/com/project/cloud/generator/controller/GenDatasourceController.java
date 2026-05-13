package com.project.cloud.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.generator.domain.dto.GenDatasourceDTO;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenDatasourceVO;
import com.project.cloud.generator.service.IGenDatasourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "数据源管理")
@RestController
@RequestMapping("/datasource")
@RequiredArgsConstructor
public class GenDatasourceController {

    private final IGenDatasourceService datasourceService;

    @PostMapping("/list")
    @Operation(summary = "数据源列表")
    public Result<PageResult<GenDatasourceVO>> list(@RequestBody GenQuery query) {
        Page<GenDatasourceVO> page = datasourceService.page(query);
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("/add")
    @Operation(summary = "新增数据源")
    @OperLog(title = "数据源管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@Valid @RequestBody GenDatasourceDTO dto) {
        datasourceService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改数据源")
    @OperLog(title = "数据源管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@Valid @RequestBody GenDatasourceDTO dto) {
        datasourceService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除数据源")
    @OperLog(title = "数据源管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        datasourceService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping("/tables")
    @Operation(summary = "获取表名列表")
    public Result<List<String>> getTableNames(@RequestBody Long datasourceId) {
        return Result.success(datasourceService.getTableNames(datasourceId));
    }
}
