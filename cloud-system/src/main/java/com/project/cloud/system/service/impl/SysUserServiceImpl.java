package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.core.result.PageResult;
import com.project.cloud.common.core.utils.SecurityUtils;
import com.project.cloud.common.core.utils.StringUtils;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.common.redis.lock.RedisLock;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.entity.SysUser;
import com.project.cloud.system.domain.query.SysUserQuery;
import com.project.cloud.system.domain.vo.SysUserVO;
import com.project.cloud.system.mapper.SysUserMapper;
import com.project.cloud.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    private final RedisLock redisLock;

    @Override
    public SysUserVO detail(SysUserQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (query.getId() != null) {
            wrapper.eq(SysUser::getId, query.getId());
        } else if (StringUtils.isNotBlank(query.getUsername())) {
            wrapper.eq(SysUser::getUsername, query.getUsername());
        } else {
            throw new BusinessException("查询参数不能为空");
        }

        SysUser user = getOne(wrapper);
        if (user == null) {
            return null;
        }

        return convertToVO(user);
    }

    @Override
    public PageResult<SysUserVO> list(SysUserQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername())
                .like(StringUtils.isNotBlank(query.getNickname()), SysUser::getNickname, query.getNickname())
                .like(StringUtils.isNotBlank(query.getPhone()), SysUser::getPhone, query.getPhone())
                .eq(query.getStatus() != null, SysUser::getStatus, query.getStatus())
                .orderByDesc(SysUser::getCreateTime);

        Page<SysUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysUser> result = page(page, wrapper);

        List<SysUserVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserDTO dto) {
        // 检查用户名是否存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, dto.getUsername());
        if (count(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAvatar(dto.getAvatar());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);

        save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        String lockKey = "lock:user:update:" + dto.getId();
        boolean locked = redisLock.tryLock(lockKey, 3, 30, TimeUnit.SECONDS);

        if (!locked) {
            throw new BusinessException("系统繁忙，请稍后重试");
        }

        try {
            SysUser user = getById(dto.getId());
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            // 检查用户名是否重复
            if (StringUtils.isNotBlank(dto.getUsername()) && !dto.getUsername().equals(user.getUsername())) {
                LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysUser::getUsername, dto.getUsername());
                if (count(wrapper) > 0) {
                    throw new BusinessException("用户名已存在");
                }
                user.setUsername(dto.getUsername());
            }

            if (StringUtils.isNotBlank(dto.getNickname())) {
                user.setNickname(dto.getNickname());
            }
            if (StringUtils.isNotBlank(dto.getEmail())) {
                user.setEmail(dto.getEmail());
            }
            if (StringUtils.isNotBlank(dto.getPhone())) {
                user.setPhone(dto.getPhone());
            }
            if (StringUtils.isNotBlank(dto.getAvatar())) {
                user.setAvatar(dto.getAvatar());
            }
            if (dto.getStatus() != null) {
                user.setStatus(dto.getStatus());
            }

            updateById(user);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("删除ID不能为空");
        }
        removeByIds(ids);
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return getOne(wrapper);
    }

    private SysUserVO convertToVO(SysUser user) {
        SysUserVO vo = new SysUserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setAvatar(user.getAvatar());
        vo.setStatus(user.getStatus());
        vo.setCreateTime(user.getCreateTime());
        vo.setUpdateTime(user.getUpdateTime());
        return vo;
    }
}
