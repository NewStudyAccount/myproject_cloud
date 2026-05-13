package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.entity.SysRole;
import com.project.cloud.system.domain.query.SysRoleQuery;
import com.project.cloud.system.domain.vo.SysRoleVO;

import java.util.List;

public interface ISysRoleService extends IService<SysRole> {

    Page<SysRoleVO> page(SysRoleQuery query);

    SysRoleVO detail(Long id);

    void add(SysRoleDTO dto);

    void update(SysRoleDTO dto);

    void deleteByIds(List<Long> ids);
}
