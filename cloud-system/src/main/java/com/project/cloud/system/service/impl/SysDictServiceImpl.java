package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.system.convert.SysDictConvert;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.entity.SysDictData;
import com.project.cloud.system.domain.entity.SysDictType;
import com.project.cloud.system.domain.query.SysDictQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.domain.vo.SysDictTypeVO;
import com.project.cloud.system.mapper.SysDictDataMapper;
import com.project.cloud.system.mapper.SysDictTypeMapper;
import com.project.cloud.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl implements ISysDictService {

    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictDataMapper dictDataMapper;

    @Override
    public Page<SysDictTypeVO> pageType(SysDictQuery query) {
        Page<SysDictType> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getDictName()), SysDictType::getDictName, query.getDictName())
                .like(StringUtils.hasText(query.getDictType()), SysDictType::getDictType, query.getDictType())
                .eq(query.getStatus() != null, SysDictType::getStatus, query.getStatus())
                .orderByDesc(SysDictType::getCreateTime);

        Page<SysDictType> result = dictTypeMapper.selectPage(page, wrapper);
        return SysDictConvert.INSTANCE.toTypePageVO(result);
    }

    @Override
    public void addType(SysDictTypeDTO dto) {
        SysDictType entity = SysDictConvert.INSTANCE.toTypeEntity(dto);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        dictTypeMapper.insert(entity);
    }

    @Override
    public void updateType(SysDictTypeDTO dto) {
        SysDictType entity = SysDictTypeMapper.selectById(dto.getId());
        if (entity == null) {
            throw new BusinessException("字典类型不存在");
        }
        SysDictType updated = SysDictConvert.INSTANCE.toTypeEntity(dto);
        dictTypeMapper.updateById(updated);
    }

    @Override
    public void deleteTypeByIds(List<Long> ids) {
        dictTypeMapper.deleteBatchIds(ids);
    }

    @Override
    public List<SysDictDataVO> listData(String dictType) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getStatus, 0)
                .orderByAsc(SysDictData::getSort);

        List<SysDictData> list = dictDataMapper.selectList(wrapper);
        return SysDictConvert.INSTANCE.toDataVOList(list);
    }

    @Override
    public void addData(SysDictDataDTO dto) {
        SysDictData entity = SysDictConvert.INSTANCE.toDataEntity(dto);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        dictDataMapper.insert(entity);
    }

    @Override
    public void updateData(SysDictDataDTO dto) {
        SysDictData entity = SysDictDataMapper.selectById(dto.getId());
        if (entity == null) {
            throw new BusinessException("字典数据不存在");
        }
        SysDictData updated = SysDictConvert.INSTANCE.toDataEntity(dto);
        dictDataMapper.updateById(updated);
    }

    @Override
    public void deleteDataByIds(List<Long> ids) {
        dictDataMapper.deleteBatchIds(ids);
    }
}
