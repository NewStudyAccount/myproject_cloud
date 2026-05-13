package com.project.cloud.generator.service;

import com.project.cloud.generator.domain.entity.GenConfig;

import java.util.Map;

public interface GenCodeService {

    Map<String, String> generateCode(GenConfig config);
}
