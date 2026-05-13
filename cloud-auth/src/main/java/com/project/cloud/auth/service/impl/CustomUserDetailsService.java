package com.project.cloud.auth.service.impl;

import com.project.cloud.api.system.RemoteUserService;
import com.project.cloud.api.system.domain.RemoteUserInfo;
import com.project.cloud.common.core.enums.UserStatus;
import com.project.cloud.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RemoteUserInfo userInfo = remoteUserService.getUserByUsername(username).getData();
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        if (UserStatus.DISABLE.getCode() == userInfo.getStatus()) {
            throw new BusinessException("用户已被停用");
        }

        List<SimpleGrantedAuthority> authorities = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        return new CustomUserDetails(userInfo.getId(), username, userInfo.getPassword(), authorities);
    }
}