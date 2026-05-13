package com.project.cloud.system.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.query.SysMenuQuery;
import com.project.cloud.system.domain.vo.SysMenuVO;
import com.project.cloud.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final ISysMenuService menuService;

    @PostMapping("/list")
    @Operation(summary = "菜单列表")
    public Result<List<SysMenuVO>> list(@RequestBody SysMenuQuery query) {
        return Result.success(menuService.list(query));
    }

    @PostMapping("/tree")
    @Operation(summary = "菜单树")
    public Result<List<SysMenuVO>> tree() {
        return Result.success(menuService.tree());
    }

    @PostMapping("/detail")
    @Operation(summary = "菜单详情")
    public Result<SysMenuVO> detail(@RequestBody SysMenuQuery query) {
        return Result.success(menuService.detail(query.getId()));
    }

    @PostMapping("/add")
    @Operation(summary = "新增菜单")
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@Valid @RequestBody SysMenuDTO dto) {
        menuService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改菜单")
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@Valid @RequestBody SysMenuDTO dto) {
        menuService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除菜单")
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody Long id) {
        menuService.deleteById(id);
        return Result.success();
    }
}
