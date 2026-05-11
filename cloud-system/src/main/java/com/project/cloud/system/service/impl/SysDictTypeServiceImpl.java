package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.entity.SysDictData;
import com.project.cloud.system.domain.entity.SysDictType;
import com.project.cloud.system.domain.query.SysDictTypeQuery;
import com.project.cloud.system.domain.vo.SysDictTypeVO;
import com.project.cloud.system.mapper.SysDictDataMapper;
import com.project.cloud.system.mapper.SysDictTypeMapper;
import com.project.cloud.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseService<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    private final SysDictDataMapper dictDataMapper;

    @Override
    public SysDictTypeVO detail(SysDictTypeQuery query) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        if (query.getId() != null) {
            wrapper.eq(SysDictType::getId, query.getId());
        } else if (StringUtils.isNotBlank(query.getDictType())) {
            wrapper.eq(SysDictType::getDictType, query.getDictType());
        } else {
            throw new BusinessException("查询参数不能为空");
        }

        SysDictType dictType = getOne(wrapper);
        if (dictType == null) {
            return null;
        }

        return convertToVO(dictType);
    }

    @Override
    public PageResult<SysDictTypeVO> list(SysDictTypeQuery query) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getDictName()), SysDictType::getDictName, query.getDictName())
                .like(StringUtils.isNotBlank(query.getDictType()), SysDictType::getDictType, query.getDictType())
                .eq(query.getStatus() != null, SysDictType::getStatus, query.getStatus())
                .orderByDesc(SysDictType::getCreateTime);

        Page<SysDictType> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysDictType> result = page(page, wrapper);

        List<SysDictTypeVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    public List<SysDictTypeVO> listAll() {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictType::getStatus, 0)
                .orderByDesc(SysDictType::getCreateTime);

        return list(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysDictTypeDTO dto) {
        // 检查字典类型是否存在
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictType::getDictType, dto.getDictType());
        if (count(wrapper) > 0) {
            throw new BusinessException("字典类型已存在");
        }

        SysDictType dictType = new SysDictType();
        dictType.setDictName(dto.getDictName());
        dictType.setDictType(dto.getDictType());
        dictType.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);

        save(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeDTO dto) {
        SysDictType dictType = getById(dto.getId());
        if (dictType == null) {
            throw new BusinessException("字典类型不存在");
        }

        // 检查字典类型是否重复
        if (StringUtils.isNotBlank(dto.getDictType()) && !dto.getDictType().equals(dictType.getDictType())) {
            LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictType::getDictType, dto.getDictType());
            if (count(wrapper) > 0) {
                throw new BusinessException("字典类型已存在");
            }
            dictType.setDictType(dto.getDictType());
        }

        if (StringUtils.isNotBlank(dto.getDictName())) {
            dictType.setDictName(dto.getDictName());
        }
        if (dto.getStatus() != null) {
            dictType.setStatus(dto.getStatus());
        }

        updateById(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }

        // 检查字典数据是否存在
        for (Long id : ids) {
            SysDictType dictType = getById(id);
            if (dictType != null) {
                LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysDictData::getDictType, dictType.getDictType());
                if (dictDataMapper.selectCount(wrapper) > 0) {
                    throw new BusinessException("字典类型【" + dictType.getDictName() + "】存在字典数据，不允许删除");
                }
            }
        }

        removeByIds(ids);
    }

    private SysDictTypeVO convertToVO(SysDictType dictType) {
        SysDictTypeVO vo = new SysDictTypeVO();
        vo.setId(dictType.getId());
        vo.setDictName(dictType.getDictName());
        vo.setDictType(dictType.getDictType());
        vo.setStatus(dictType.getStatus());
        vo.setCreateTime(dictType.getCreateTime());
        vo.setUpdateTime(dictType.getUpdateTime());
        return vo;
    }
}
