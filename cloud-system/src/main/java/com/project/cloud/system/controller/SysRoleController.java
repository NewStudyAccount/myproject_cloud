package com.project.cloud.system.controller;

import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.query.SysRoleQuery;
import com.project.cloud.system.domain.vo.SysRoleVO;
import com.project.cloud.system.service.ISysRoleService;
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
 * 角色管理控制器
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService roleService;

    @PostMapping("/detail")
    @Operation(summary = "查询角色详情")
    public Result<SysRoleVO> detail(@RequestBody SysRoleQuery query) {
        return Result.success(roleService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询角色列表")
    public Result<PageResult<SysRoleVO>> list(@RequestBody SysRoleQuery query) {
        return Result.success(roleService.list(query));
    }

    @PostMapping("/listAll")
    @Operation(summary = "查询所有角色")
    public Result<List<SysRoleVO>> listAll() {
        return Result.success(roleService.listAll());
    }

    @PostMapping("/add")
    @Operation(summary = "新增角色")
    @OperLog(title = "角色管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid SysRoleDTO dto) {
        roleService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid SysRoleDTO dto) {
        roleService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除角色")
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        roleService.delete(ids);
        return Result.success();
    }
}
