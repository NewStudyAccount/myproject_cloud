package com.project.cloud.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.generator.convert.GenConvert;
import com.project.cloud.generator.domain.dto.GenConfigDTO;
import com.project.cloud.generator.domain.entity.GenConfig;
import com.project.cloud.generator.domain.query.GenQuery;
import com.project.cloud.generator.domain.vo.GenConfigVO;
import com.project.cloud.generator.mapper.GenConfigMapper;
import com.project.cloud.generator.service.GenCodeService;
import com.project.cloud.generator.service.IGenConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends BaseService<GenConfigMapper, GenConfig> implements IGenConfigService {

    private final GenCodeService genCodeService;

    @Override
    public Page<GenConfigVO> page(GenQuery query) {
        Page<GenConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<GenConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getTableName() != null, GenConfig::getTableName, query.getTableName())
                .orderByDesc(GenConfig::getCreateTime);

        Page<GenConfig> result = baseMapper.selectPage(page, wrapper);
        return GenConvert.INSTANCE.toConfigPageVO(result);
    }

    @Override
    public void add(GenConfigDTO dto) {
        GenConfig entity = GenConvert.INSTANCE.toConfigEntity(dto);
        entity.setStatus(dto.getStatus() != null ? 0 : 0);
        save(entity);
    }

    @Override
    public void update(GenConfigDTO dto) {
        GenConfig entity = GenConvert.INSTANCE.toConfigEntity(dto);
        updateById(entity);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public Map<String, String> previewCode(Long configId) {
        GenConfig config = getById(configId);
        if (config == null) {
            return Map.of();
        }
        return genCodeService.generateCode(config);
    }

    @Override
    public byte[] downloadCode(Long configId) {
        GenConfig config = getById(configId);
        if (config == null) {
            return new byte[0];
        }
        Map<String, String> codeMap = genCodeService.generateCode(config);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Map.Entry<String, String> entry : codeMap.entrySet()) {
                zos.putNextEntry(new java.util.zip.ZipEntry(entry.getKey()));
                zos.write(entry.getValue().getBytes());
                zos.closeEntry();
            }
            zos.finish();
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("生成代码下载包失败", e);
            return new byte[0];
        }
    }
}
