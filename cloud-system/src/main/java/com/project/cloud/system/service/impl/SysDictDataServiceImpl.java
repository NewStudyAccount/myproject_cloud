package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.entity.SysDictData;
import com.project.cloud.system.domain.query.SysDictDataQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.mapper.SysDictDataMapper;
import com.project.cloud.system.service.ISysDictDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends BaseService<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Override
    public SysDictDataVO detail(SysDictDataQuery query) {
        if (query.getId() == null) {
            throw new BusinessException("字典数据ID不能为空");
        }

        SysDictData dictData = getById(query.getId());
        if (dictData == null) {
            return null;
        }

        return convertToVO(dictData);
    }

    @Override
    public PageResult<SysDictDataVO> list(SysDictDataQuery query) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(query.getDictType()), SysDictData::getDictType, query.getDictType())
                .like(StringUtils.isNotBlank(query.getDictLabel()), SysDictData::getDictLabel, query.getDictLabel())
                .eq(query.getStatus() != null, SysDictData::getStatus, query.getStatus())
                .orderByAsc(SysDictData::getSort);

        Page<SysDictData> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysDictData> result = page(page, wrapper);

        List<SysDictDataVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    public List<SysDictDataVO> listByDictType(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            throw new BusinessException("字典类型不能为空");
        }

        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getStatus, 0)
                .orderByAsc(SysDictData::getSort);

        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictDataDTO dto) {
        SysDictData dictData = new SysDictData();
        dictData.setDictType(dto.getDictType());
        dictData.setDictLabel(dto.getDictLabel());
        dictData.setDictValue(dto.getDictValue());
        dictData.setSort(dto.getSort() != null ? dto.getSort() : 0);
        dictData.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);

        save(dictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataDTO dto) {
        SysDictData dictData = getById(dto.getId());
        if (dictData == null) {
            throw new BusinessException("字典数据不存在");
        }

        if (StringUtils.isNotBlank(dto.getDictType())) {
            dictData.setDictType(dto.getDictType());
        }
        if (StringUtils.isNotBlank(dto.getDictLabel())) {
            dictData.setDictLabel(dto.getDictLabel());
        }
        if (StringUtils.isNotBlank(dto.getDictValue())) {
            dictData.setDictValue(dto.getDictValue());
        }
        if (dto.getSort() != null) {
            dictData.setSort(dto.getSort());
        }
        if (dto.getStatus() != null) {
            dictData.setStatus(dto.getStatus());
        }

        updateById(dictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }
        removeByIds(ids);
    }

    private SysDictDataVO convertToVO(SysDictData dictData) {
        SysDictDataVO vo = new SysDictDataVO();
        vo.setId(dictData.getId());
        vo.setDictType(dictData.getDictType());
        vo.setDictLabel(dictData.getDictLabel());
        vo.setDictValue(dictData.getDictValue());
        vo.setSort(dictData.getSort());
        vo.setStatus(dictData.getStatus());
        vo.setCreateTime(dictData.getCreateTime());
        vo.setUpdateTime(dictData.getUpdateTime());
        return vo;
    }
}
