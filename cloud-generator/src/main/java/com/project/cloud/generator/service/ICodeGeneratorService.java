package com.project.cloud.generator.service;

import com.project.cloud.generator.domain.entity.GenConfig;

import java.util.Map;

/**
 * 代码生成服务接口
 */
public interface ICodeGeneratorService {

    /**
     * 生成代码
     */
    Map<String, String> generate(GenConfig config);

    /**
     * 预览代码
     */
    Map<String, String> preview(GenConfig config);
}
