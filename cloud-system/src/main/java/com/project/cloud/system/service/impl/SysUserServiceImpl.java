package com.project.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.cloud.common.core.exception.BusinessException;
import com.project.cloud.common.mybatis.base.BaseService;
import com.project.cloud.system.convert.SysUserConvert;
import com.project.cloud.system.domain.dto.SysUserDTO;
import com.project.cloud.system.domain.entity.SysUser;
import com.project.cloud.system.domain.query.SysUserQuery;
import com.project.cloud.system.domain.vo.SysUserVO;
import com.project.cloud.system.mapper.SysUserMapper;
import com.project.cloud.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<SysUserVO> page(SysUserQuery query) {
        Page<SysUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getUsername()), SysUser::getUsername, query.getUsername())
                .like(StringUtils.hasText(query.getNickname()), SysUser::getNickname, query.getNickname())
                .like(StringUtils.hasText(query.getPhone()), SysUser::getPhone, query.getPhone())
                .eq(query.getStatus() != null, SysUser::getStatus, query.getStatus())
                .orderByDesc(SysUser::getCreateTime);

        Page<SysUser> result = baseMapper.selectPage(page, wrapper);
        return SysUserConvert.INSTANCE.toPageVO(result);
    }

    @Override
    public SysUserVO detail(Long id) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return SysUserConvert.INSTANCE.toVO(user);
    }

    @Override
    public void add(SysUserDTO dto) {
        // 检查用户名唯一
        SysUser existing = getByUsername(dto.getUsername());
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }

        SysUser user = SysUserConvert.INSTANCE.toEntity(dto);
        user.setPassword(passwordEncoder.encode(
                StringUtils.hasText(dto.getPassword()) ? dto.getPassword() : "123456"
        ));
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        save(user);
    }

    @Override
    public void update(SysUserDTO dto) {
        SysUser user = getById(dto.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        SysUser updated = SysUserConvert.INSTANCE.toEntity(dto);
        updated.setPassword(null); // 不更新密码
        updateById(updated);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .last("LIMIT 1"));
    }
}
