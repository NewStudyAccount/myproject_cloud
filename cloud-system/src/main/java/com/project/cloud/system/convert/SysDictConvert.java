package com.project.cloud.system.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.entity.SysDictData;
import com.project.cloud.system.domain.entity.SysDictType;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.domain.vo.SysDictTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictConvert {

    SysDictConvert INSTANCE = Mappers.getMapper(SysDictConvert.class);

    SysDictTypeVO toTypeVO(SysDictType entity);

    List<SysDictTypeVO> toTypeVOList(List<SysDictType> list);

    SysDictType toTypeEntity(SysDictTypeDTO dto);

    default Page<SysDictTypeVO> toTypePageVO(Page<SysDictType> page) {
        Page<SysDictTypeVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(toTypeVOList(page.getRecords()));
        return voPage;
    }

    SysDictDataVO toDataVO(SysDictData entity);

    List<SysDictDataVO> toDataVOList(List<SysDictData> list);

    SysDictData toDataEntity(SysDictDataDTO dto);
}
