package com.project.cloud.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.domain.query.GenConfigQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;

import java.util.List;
import java.util.Map;

/**
 * 代码生成配置服务接口
 */
public interface IGenConfigService extends IService<GenConfig> {

    /**
     * 查询配置详情
     */
    GenConfigVO detail(GenConfigQuery query);

    /**
     * 查询配置列表（分页）
     */
    PageResult<GenConfigVO> list(GenConfigQuery query);

    /**
     * 新增配置
     */
    void add(GenConfigDTO dto);

    /**
     * 更新配置
     */
    void update(GenConfigDTO dto);

    /**
     * 删除配置
     */
    void delete(List<Long> ids);

    /**
     * 生成代码
     */
    Map<String, String> generateCode(Long configId);

    /**
     * 预览代码
     */
    Map<String, String> previewCode(Long configId);
}
