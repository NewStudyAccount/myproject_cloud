package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.convert.SysRoleConvert;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.entity.SysRole;
import com.project.cloud.system.domain.query.SysRoleQuery;
import com.project.cloud.system.domain.vo.SysRoleVO;
import com.project.cloud.system.mapper.SysRoleMapper;
import com.project.cloud.system.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class SysRoleServiceImpl extends BaseService<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public Page<SysRoleVO> page(SysRoleQuery query) {
        Page<SysRole> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getRoleName()), SysRole::getRoleName, query.getRoleName())
                .like(StringUtils.hasText(query.getRoleKey()), SysRole::getRoleKey, query.getRoleKey())
                .eq(query.getStatus() != null, SysRole::getStatus, query.getStatus())
                .orderByAsc(SysRole::getSort);

        Page<SysRole> result = baseMapper.selectPage(page, wrapper);
        return SysRoleConvert.INSTANCE.toPageVO(result);
    }

    @Override
    public SysRoleVO detail(Long id) {
        SysRole role = getById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        return SysRoleConvert.INSTANCE.toVO(role);
    }

    @Override
    public void add(SysRoleDTO dto) {
        SysRole role = SysRoleConvert.INSTANCE.toEntity(dto);
        role.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        save(role);
    }

    @Override
    public void update(SysRoleDTO dto) {
        SysRole role = getById(dto.getId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        SysRole updated = SysRoleConvert.INSTANCE.toEntity(dto);
        updateById(updated);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }
}
