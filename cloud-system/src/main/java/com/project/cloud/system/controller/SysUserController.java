package com.project.cloud.system.controller;

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

/**
 * 用户管理控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;

    @PostMapping("/detail")
    @Operation(summary = "查询用户详情")
    public Result<SysUserVO> detail(@RequestBody SysUserQuery query) {
        return Result.success(userService.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询用户列表")
    public Result<PageResult<SysUserVO>> list(@RequestBody SysUserQuery query) {
        return Result.success(userService.list(query));
    }

    @PostMapping("/add")
    @Operation(summary = "新增用户")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public Result<Void> add(@RequestBody @Valid SysUserDTO dto) {
        userService.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public Result<Void> update(@RequestBody @Valid SysUserDTO dto) {
        userService.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除用户")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    public Result<Void> delete(@RequestBody List<Long> ids) {
        userService.delete(ids);
        return Result.success();
    }
}
