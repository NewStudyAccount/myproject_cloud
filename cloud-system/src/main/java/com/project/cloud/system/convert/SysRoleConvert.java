package com.project.cloud.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.system.domain.dto.SysRoleDTO;
import com.project.cloud.system.domain.entity.SysRole;
import com.project.cloud.system.domain.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleVO toVO(SysRole entity);

    List<SysRoleVO> toVOList(List<SysRole> list);

    SysRole toEntity(SysRoleDTO dto);

    default Page<SysRoleVO> toPageVO(Page<SysRole> page) {
        Page<SysRoleVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(toVOList(page.getRecords()));
        return voPage;
    }
}
