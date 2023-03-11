package com.lzhphantom.security.service.impl;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.security.service.LzhphantomUser;
import com.lzhphantom.security.service.LzhphantomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

@Log4j2
@Primary
@RequiredArgsConstructor
public class LzhphantomUserDetailsServiceImpl implements LzhphantomUserDetailsService {
    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @return
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (Objects.nonNull(cache) && Objects.nonNull(cache.get(username))) {
            return cache.get(username, LzhphantomUser.class);
        }
        LzhphantomResult<UserInfo> result = remoteUserService.info(username);
        UserDetails userDetails = getUserDetails(result);
        if (Objects.nonNull(cache)) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
