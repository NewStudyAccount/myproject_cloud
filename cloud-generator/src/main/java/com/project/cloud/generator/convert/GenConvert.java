package com.project.cloud.generator.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.dto.GenDatasourceDTO;
import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.domain.entity.GenDatasource;
import com.project.cloud.generator.domain.vo.GenConfigVO;
import com.project.cloud.generator.domain.vo.GenDatasourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenConvert {

    GenConvert INSTANCE = Mappers.getMapper(GenConvert.class);

    GenDatasourceVO toDatasourceVO(GenDatasource entity);

    List<GenDatasourceVO> toDatasourceVOList(List<GenDatasource> list);

    GenDatasource toDatasourceEntity(GenDatasourceDTO dto);

    default Page<GenDatasourceVO> toDatasourcePageVO(Page<GenDatasource> page) {
        Page<GenDatasourceVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(toDatasourceVOList(page.getRecords()));
        return voPage;
    }

    GenConfigVO toConfigVO(GenConfig entity);

    List<GenConfigVO> toConfigVOList(List<GenConfig> list);

    GenConfig toConfigEntity(GenConfigDTO dto);

    default Page<GenConfigVO> toConfigPageVO(Page<GenConfig> page) {
        Page<GenConfigVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(toConfigVOList(page.getRecords()));
        return voPage;
    }
}
