package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.convert.SysMenuConvert;
import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.entity.SysMenu;
import com.project.cloud.system.domain.query.SysMenuQuery;
import com.project.cloud.system.domain.vo.SysMenuVO;
import com.project.cloud.system.mapper.SysMenuMapper;
import com.project.cloud.system.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenuVO> list(SysMenuQuery query) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getMenuName()), SysMenu::getMenuName, query.getMenuName())
                .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
                .orderByAsc(SysMenu::getSort);

        List<SysMenu> list = list(wrapper);
        return SysMenuConvert.INSTANCE.toVOList(list);
    }

    @Override
    public List<SysMenuVO> tree() {
        List<SysMenu> allMenus = list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getStatus, 0)
                .orderByAsc(SysMenu::getSort));

        List<SysMenuVO> voList = SysMenuConvert.INSTANCE.toVOList(allMenus);
        return buildTree(voList, 0L);
    }

    @Override
    public SysMenuVO detail(Long id) {
        SysMenu menu = getById(id);
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }
        return SysMenuConvert.INSTANCE.toVO(menu);
    }

    @Override
    public void add(SysMenuDTO dto) {
        SysMenu menu = SysMenuConvert.INSTANCE.toEntity(dto);
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        menu.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        save(menu);
    }

    @Override
    public void update(SysMenuDTO dto) {
        SysMenu menu = getById(dto.getId());
        if (menu == null) {
            throw new BusinessException("菜单不存在");
        }
        SysMenu updated = SysMenuConvert.INSTANCE.toEntity(dto);
        updateById(updated);
    }

    @Override
    public void deleteById(Long id) {
        // 检查是否有子菜单
        long childCount = count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (childCount > 0) {
            throw new BusinessException("存在子菜单，不允许删除");
        }
        removeById(id);
    }

    private List<SysMenuVO> buildTree(List<SysMenuVO> list, Long parentId) {
        Map<Long, List<SysMenuVO>> grouped = list.stream()
                .collect(Collectors.groupingBy(SysMenuVO::getParentId));

        return buildChildren(grouped, parentId);
    }

    private List<SysMenuVO> buildChildren(Map<Long, List<SysMenuVO>> grouped, Long parentId) {
        List<SysMenuVO> children = grouped.getOrDefault(parentId, new ArrayList<>());
        for (SysMenuVO child : children) {
            child.setChildren(buildChildren(grouped, child.getId()));
        }
        return children;
    }
}
