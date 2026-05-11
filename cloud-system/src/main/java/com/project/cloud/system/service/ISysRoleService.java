package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.entity.SysRole;
import com.project.cloud.system.domain.query.SysRoleQuery;
import com.project.cloud.system.domain.vo.SysRoleVO;

import java.util.List;

/**
 * 角色服务接口
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 查询角色详情
     */
    SysRoleVO detail(SysRoleQuery query);

    /**
     * 查询角色列表（分页）
     */
    PageResult<SysRoleVO> list(SysRoleQuery query);

    /**
     * 查询所有角色
     */
    List<SysRoleVO> listAll();

    /**
     * 新增角色
     */
    void add(SysRoleDTO dto);

    /**
     * 更新角色
     */
    void update(SysRoleDTO dto);

    /**
     * 删除角色
     */
    void delete(List<Long> ids);
}
