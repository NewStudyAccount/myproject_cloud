package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.entity.SysRole;
import com.project.cloud.system.domain.entity.SysRoleMenu;
import com.project.cloud.system.domain.query.SysRoleQuery;
import com.project.cloud.system.domain.vo.SysRoleVO;
import com.project.cloud.system.mapper.SysRoleMapper;
import com.project.cloud.system.mapper.SysRoleMenuMapper;
import com.project.cloud.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseService<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    public SysRoleVO detail(SysRoleQuery query) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (query.getId() != null) {
            wrapper.eq(SysRole::getId, query.getId());
        } else if (StringUtils.isNotBlank(query.getRoleKey())) {
            wrapper.eq(SysRole::getRoleKey, query.getRoleKey());
        } else {
            throw new BusinessException("查询参数不能为空");
        }

        SysRole role = getOne(wrapper);
        if (role == null) {
            return null;
        }

        return convertToVO(role);
    }

    @Override
    public PageResult<SysRoleVO> list(SysRoleQuery query) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getRoleName()), SysRole::getRoleName, query.getRoleName())
                .like(StringUtils.isNotBlank(query.getRoleKey()), SysRole::getRoleKey, query.getRoleKey())
                .eq(query.getStatus() != null, SysRole::getStatus, query.getStatus())
                .orderByAsc(SysRole::getSort);

        Page<SysRole> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysRole> result = page(page, wrapper);

        List<SysRoleVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    public List<SysRoleVO> listAll() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getStatus, 0)
                .orderByAsc(SysRole::getSort);

        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysRoleDTO dto) {
        // 检查角色权限字符串是否存在
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleKey, dto.getRoleKey());
        if (count(wrapper) > 0) {
            throw new BusinessException("角色权限字符串已存在");
        }

        SysRole role = new SysRole();
        role.setRoleName(dto.getRoleName());
        role.setRoleKey(dto.getRoleKey());
        role.setSort(dto.getSort() != null ? dto.getSort() : 0);
        role.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);

        save(role);

        // 保存角色菜单关联
        if (dto.getMenuIds() != null && !dto.getMenuIds().isEmpty()) {
            saveRoleMenus(role.getId(), dto.getMenuIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDTO dto) {
        SysRole role = getById(dto.getId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 检查角色权限字符串是否重复
        if (StringUtils.isNotBlank(dto.getRoleKey()) && !dto.getRoleKey().equals(role.getRoleKey())) {
            LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRole::getRoleKey, dto.getRoleKey());
            if (count(wrapper) > 0) {
                throw new BusinessException("角色权限字符串已存在");
            }
            role.setRoleKey(dto.getRoleKey());
        }

        if (StringUtils.isNotBlank(dto.getRoleName())) {
            role.setRoleName(dto.getRoleName());
        }
        if (dto.getSort() != null) {
            role.setSort(dto.getSort());
        }
        if (dto.getStatus() != null) {
            role.setStatus(dto.getStatus());
        }

        updateById(role);

        // 更新角色菜单关联
        if (dto.getMenuIds() != null) {
            // 删除原有关联
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getRoleId, dto.getId());
            roleMenuMapper.delete(wrapper);

            // 保存新关联
            if (!dto.getMenuIds().isEmpty()) {
                saveRoleMenus(dto.getId(), dto.getMenuIds());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }

        // 删除角色
        removeByIds(ids);

        // 删除角色菜单关联
        for (Long roleId : ids) {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getRoleId, roleId);
            roleMenuMapper.delete(wrapper);
        }
    }

    private void saveRoleMenus(Long roleId, List<Long> menuIds) {
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    private SysRoleVO convertToVO(SysRole role) {
        SysRoleVO vo = new SysRoleVO();
        vo.setId(role.getId());
        vo.setRoleName(role.getRoleName());
        vo.setRoleKey(role.getRoleKey());
        vo.setSort(role.getSort());
        vo.setStatus(role.getStatus());
        vo.setCreateTime(role.getCreateTime());
        vo.setUpdateTime(role.getUpdateTime());

        // 查询角色菜单关联
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, role.getId());
        List<Long> menuIds = roleMenuMapper.selectList(wrapper).stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        vo.setMenuIds(menuIds);

        return vo;
    }
}
