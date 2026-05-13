package com.project.cloud.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;

import java.util.List;
import java.util.Map;

public interface IGenConfigService extends IService<GenConfig> {

    Page<GenConfigVO> page(GenQuery query);

    void add(GenConfigDTO dto);

    void update(GenConfigDTO dto);

    void deleteByIds(List<Long> ids);

    Map<String, String> previewCode(Long configId);

    byte[] downloadCode(Long configId);
}
