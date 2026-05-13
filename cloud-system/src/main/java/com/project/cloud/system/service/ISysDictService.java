package com.project.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.system.domain.dto.SysDictDataDTO;
import com.project.cloud.system.domain.dto.SysDictTypeDTO;
import com.project.cloud.system.domain.query.SysDictQuery;
import com.project.cloud.system.domain.vo.SysDictDataVO;
import com.project.cloud.system.domain.vo.SysDictTypeVO;

import java.util.List;

public interface ISysDictService {

    Page<SysDictTypeVO> pageType(SysDictQuery query);

    void addType(SysDictTypeDTO dto);

    void updateType(SysDictTypeDTO dto);

    void deleteTypeByIds(List<Long> ids);

    List<SysDictDataVO> listData(String dictType);

    void addData(SysDictDataDTO dto);

    void updateData(SysDictDataDTO dto);

    void deleteDataByIds(List<Long> ids);
}
