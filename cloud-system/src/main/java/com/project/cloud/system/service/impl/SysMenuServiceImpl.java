package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.entity.SysMenu;
import com.project.cloud.system.domain.query.SysMenuQuery;
import com.project.cloud.system.domain.vo.SysMenuVO;
import com.project.cloud.system.mapper.SysMenuMapper;
import com.project.cloud.system.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public SysMenuVO detail(SysMenuQuery query) {
        if (query.getId() == null) {
            throw new BusinessException("菜单ID不能为空");
        }

        SysMenu menu = getById(query.getId());
        if (menu == null) {
            return null;
        }

        return convertToVO(menu);
    }

    @Override
    public List<SysMenuVO> list(SysMenuQuery query) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getMenuName()), SysMenu::getMenuName, query.getMenuName())
                .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
                .eq(StringUtils.isNotBlank(query.getMenuType()), SysMenu::getMenuType, query.getMenuType())
                .orderByAsc(SysMenu::getSort);

        List<SysMenu> menus = list(wrapper);
        List<SysMenuVO> voList = menus.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return buildTree(voList, 0L);
    }

    @Override
    public List<SysMenuVO> tree() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getStatus, 0)
                .orderByAsc(SysMenu::getSort);

        List<SysMenu> menus = list(wrapper);
        List<SysMenuVO> voList = menus.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return buildTree(voList, 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMenuDTO dto) {
        SysMenu menu = new SysMenu();
        menu.setMenuName(dto.getMenuName());
        menu.setParentId(dto.getParentId() != null ? dto.getParentId() : 0L);
        menu.setSort(dto.getSort() != null ? dto.getSort() : 0);
        menu.setPath(dto.getPath());
        menu.setComponent(dto.getComponent());
        menu.setMenuType(dto.getMenuType());
        menu.setPerms(dto.getPerms());
        menu.setIcon(dto.getIcon());
        menu.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        menu.setCreateBy("system");
        menu.setCreateTime(LocalDateTime.now());
        menu.setDeleted(0);

        save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException("菜单ID不能为空");
        }

        SysMenu menu = getById(dto.getId());
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }

        // 不能将自己设为自己的子菜单
        if (dto.getParentId() != null && dto.getParentId().equals(dto.getId())) {
            throw new BusinessException("上级菜单不能选择自己");
        }

        if (StringUtils.isNotBlank(dto.getMenuName())) {
            menu.setMenuName(dto.getMenuName());
        }
        if (dto.getParentId() != null) {
            menu.setParentId(dto.getParentId());
        }
        if (dto.getSort() != null) {
            menu.setSort(dto.getSort());
        }
        if (StringUtils.isNotBlank(dto.getPath())) {
            menu.setPath(dto.getPath());
        }
        if (StringUtils.isNotBlank(dto.getComponent())) {
            menu.setComponent(dto.getComponent());
        }
        if (StringUtils.isNotBlank(dto.getMenuType())) {
            menu.setMenuType(dto.getMenuType());
        }
        if (StringUtils.isNotBlank(dto.getPerms())) {
            menu.setPerms(dto.getPerms());
        }
        if (StringUtils.isNotBlank(dto.getIcon())) {
            menu.setIcon(dto.getIcon());
        }
        if (dto.getStatus() != null) {
            menu.setStatus(dto.getStatus());
        }
        menu.setUpdateBy("system");
        menu.setUpdateTime(LocalDateTime.now());

        updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (id == null) {
            throw new BusinessException("菜单ID不能为空");
        }

        // 检查是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);
        if (count(wrapper) > 0) {
            throw new BusinessException("存在子菜单,不允许删除");
        }

        removeById(id);
    }

    private List<SysMenuVO> buildTree(List<SysMenuVO> menus, Long parentId) {
        List<SysMenuVO> tree = new ArrayList<>();
        for (SysMenuVO menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                List<SysMenuVO> children = buildTree(menus, menu.getId());
                menu.setChildren(children.isEmpty() ? null : children);
                tree.add(menu);
            }
        }
        return tree;
    }

    private SysMenuVO convertToVO(SysMenu menu) {
        SysMenuVO vo = new SysMenuVO();
        vo.setId(menu.getId());
        vo.setMenuName(menu.getMenuName());
        vo.setParentId(menu.getParentId());
        vo.setSort(menu.getSort());
        vo.setPath(menu.getPath());
        vo.setComponent(menu.getComponent());
        vo.setMenuType(menu.getMenuType());
        vo.setPerms(menu.getPerms());
        vo.setIcon(menu.getIcon());
        vo.setStatus(menu.getStatus());
        vo.setCreateTime(menu.getCreateTime());
        vo.setUpdateTime(menu.getUpdateTime());
        return vo;
    }
}
