package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.entity.SysUser;
import com.project.cloud.system.domain.query.SysUserQuery;
import com.project.cloud.system.domain.vo.SysUserVO;

public interface ISysUserService extends IService<SysUser> {

    Page<SysUserVO> page(SysUserQuery query);

    SysUserVO detail(Long id);

    void add(SysUserDTO dto);

    void update(SysUserDTO dto);

    void deleteByIds(java.util.List<Long> ids);

    SysUser getByUsername(String username);
}
