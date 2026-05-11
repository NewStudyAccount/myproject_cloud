package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.entity.SysDictData;
import com.project.cloud.system.domain.query.SysDictDataQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;

import java.util.List;

/**
 * 字典数据服务接口
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 查询字典数据详情
     */
    SysDictDataVO detail(SysDictDataQuery query);

    /**
     * 查询字典数据列表（分页）
     */
    PageResult<SysDictDataVO> list(SysDictDataQuery query);

    /**
     * 根据字典类型查询字典数据
     */
    List<SysDictDataVO> listByDictType(String dictType);

    /**
     * 新增字典数据
     */
    void add(SysDictDataDTO dto);

    /**
     * 更新字典数据
     */
    void update(SysDictDataDTO dto);

    /**
     * 删除字典数据
     */
    void delete(List<Long> ids);
}
