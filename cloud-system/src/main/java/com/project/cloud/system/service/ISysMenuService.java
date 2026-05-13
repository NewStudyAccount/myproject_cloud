package com.project.cloud.system.service;

import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.entity.SysMenu;
import com.project.cloud.system.domain.query.SysMenuQuery;
import com.project.cloud.system.domain.vo.SysMenuVO;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuVO> list(SysMenuQuery query);

    List<SysMenuVO> tree();

    SysMenuVO detail(Long id);

    void add(SysMenuDTO dto);

    void update(SysMenuDTO dto);

    void deleteById(Long id);
}
