package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.entity.SysUser;
import com.project.cloud.system.domain.query.SysUserQuery;
import com.project.cloud.system.domain.vo.SysUserVO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 查询用户详情
     */
    SysUserVO detail(SysUserQuery query);

    /**
     * 查询用户列表（分页）
     */
    PageResult<SysUserVO> list(SysUserQuery query);

    /**
     * 新增用户
     */
    void add(SysUserDTO dto);

    /**
     * 更新用户
     */
    void update(SysUserDTO dto);

    /**
     * 删除用户
     */
    void delete(List<Long> ids);

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);
}
