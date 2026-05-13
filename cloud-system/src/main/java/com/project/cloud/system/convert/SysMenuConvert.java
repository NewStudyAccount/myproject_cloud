package com.project.cloud.system.convert;

import com.project.cloud.system.domain.dto.SysMenuDTO;
import com.project.cloud.system.domain.entity.SysMenu;
import com.project.cloud.system.domain.vo.SysMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuVO toVO(SysMenu entity);

    List<SysMenuVO> toVOList(List<SysMenu> list);

    SysMenu toEntity(SysMenuDTO dto);
}
