package com.lzhphantom.security.service.impl;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.security.service.LzhphantomUser;
import com.lzhphantom.security.service.LzhphantomUserDetailsService;
import com.lzhphantom.user.dto.UserInfo;
import com.lzhphantom.user.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 用户详细信息
 *
 * @author lzhphantom
 */
@Log4j2
@Primary
@RequiredArgsConstructor
public class LzhphantomAppUserDetailsServiceImpl implements LzhphantomUserDetailsService {
    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * check-token 使用
     * @param user
     * @return
     */
    @Override
    public UserDetails loadUserByUser(LzhphantomUser user) {
        return this.loadUserByUsername(user.getPhone());
    }

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (Objects.nonNull(cache) && Objects.nonNull(cache.get(phone))) {
            return cache.get(phone, LzhphantomUser.class);
        }
        LzhphantomResult<UserInfo> result = remoteUserService.infoByMobile(phone);
        UserDetails userDetails = getUserDetails(result);
        if (Objects.nonNull(cache)) {
            cache.put(phone, userDetails);
        }
        return userDetails;
    }

    /**
     * 是否支持此客户端校验
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return SecurityConstants.APP.equals(grantType);
    }
}
