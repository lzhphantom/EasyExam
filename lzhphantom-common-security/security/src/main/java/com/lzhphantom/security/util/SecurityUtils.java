package com.lzhphantom.security.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.security.service.LzhphantomUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 安全工具类
 *
 * @author lzhphantom
 */
@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public LzhphantomUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof LzhphantomUser) {
            return (LzhphantomUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public LzhphantomUser getUser(){
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication)){
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取用户角色信息
     * @return 角色集合
     */
    public List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Long> roleIds = Lists.newArrayList();
        authorities.stream()
                .filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Long.parseLong(id));
                });
        return roleIds;
    }
}
