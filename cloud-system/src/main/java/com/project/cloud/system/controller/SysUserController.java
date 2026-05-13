package com.project.cloud.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.result.Result;
import com.project.cloud.common.log.annotation.OperLog;
import com.project.cloud.common.core.enums.BusinessType;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.query.SysUserQuery;
import com.project.cloud.system.domain.vo.SysUserVO;
import com.project.cloud.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;

    @PostMapping("/list")
    @Operation(summary = "用户列表")
    public Result<PageResult<SysUserVO>> list(@RequestBody SysUserQuery query) {
        Page<SysUserVO> page = userService.page(query);
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("/detail")
    @Operation(summary = "用户详情")
    public Result<SysUserVO> detail(@RequestBody SysUserQuery query) {
        return Result.success(userService.detail(query.getId()));
    }

    @PostMapping("/add")
    @Operation(summary = "新增用户")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@Valid @RequestBody SysUserDTO dto) {
        userService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@Valid @RequestBody SysUserDTO dto) {
        userService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除用户")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        userService.deleteByIds(ids);
        return Result.success();
    }
}
