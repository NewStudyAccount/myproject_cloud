package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.entity.SysDictType;
import com.project.cloud.system.domain.query.SysDictTypeQuery;
import com.project.cloud.system.domain.vo.SysDictTypeVO;

import java.util.List;

/**
 * 字典类型服务接口
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 查询字典类型详情
     */
    SysDictTypeVO detail(SysDictTypeQuery query);

    /**
     * 查询字典类型列表（分页）
     */
    PageResult<SysDictTypeVO> list(SysDictTypeQuery query);

    /**
     * 查询所有字典类型
     */
    List<SysDictTypeVO> listAll();

    /**
     * 新增字典类型
     */
    void add(SysDictTypeDTO dto);

    /**
     * 更新字典类型
     */
    void update(SysDictTypeDTO dto);

    /**
     * 删除字典类型
     */
    void delete(List<Long> ids);
}
