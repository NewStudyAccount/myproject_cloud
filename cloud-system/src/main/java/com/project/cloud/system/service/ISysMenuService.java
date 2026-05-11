package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.entity.SysMenu;
import com.project.cloud.system.domain.query.SysMenuQuery;
import com.project.cloud.system.domain.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单详情
     */
    SysMenuVO detail(SysMenuQuery query);

    /**
     * 查询菜单列表（树形）
     */
    List<SysMenuVO> list(SysMenuQuery query);

    /**
     * 查询菜单树
     */
    List<SysMenuVO> tree();

    /**
     * 新增菜单
     */
    void add(SysMenuDTO dto);

    /**
     * 更新菜单
     */
    void update(SysMenuDTO dto);

    /**
     * 删除菜单
     */
    void delete(Long id);
}
