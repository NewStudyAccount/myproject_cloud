package com.project.cloud.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.entity.SysUser;
import com.project.cloud.system.domain.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVO toVO(SysUser entity);

    List<SysUserVO> toVOList(List<SysUser> list);

    SysUser toEntity(SysUserDTO dto);

    default Page<SysUserVO> toPageVO(Page<SysUser> page) {
        Page<SysUserVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(toVOList(page.getRecords()));
        return voPage;
    }
}
