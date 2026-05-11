package com.project.cloud.common.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 基础 Service 实现类
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 根据 ID 查询
     */
    public T selectById(Long id) {
        return getById(id);
    }

    /**
     * 查询所有
     */
    public java.util.List<T> selectAll() {
        return list();
    }

    /**
     * 插入
     */
    public boolean insert(T entity) {
        return save(entity);
    }

    /**
     * 批量插入
     */
    public boolean insertBatch(java.util.List<T> entityList) {
        return saveBatch(entityList);
    }

    /**
     * 更新
     */
    public boolean update(T entity) {
        return updateById(entity);
    }

    /**
     * 批量更新
     */
    public boolean updateBatch(java.util.List<T> entityList) {
        return updateBatchById(entityList);
    }

    /**
     * 删除
     */
    public boolean deleteById(Long id) {
        return removeById(id);
    }

    /**
     * 批量删除
     */
    public boolean deleteBatchIds(java.util.Collection<Long> ids) {
        return removeByIds(ids);
    }
}
