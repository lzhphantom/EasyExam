package com.lzhphantom.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.RetOps;
import com.lzhphantom.core.constant.CommonConstants;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.user.dto.UserInfo;
import com.lzhphantom.user.login.entity.User;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * @author lzhphantom
 */
public interface LzhphantomUserDetailsService extends UserDetailsService, Ordered {
    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return UserDetails
     */
    default UserDetails getUserDetails(LzhphantomResult<UserInfo> result) {
        UserInfo info = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        Set<String> dbAuthsSet = Sets.newHashSet();

        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }

        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        User user = info.getUser();

        // 构造security用户
        return new LzhphantomUser(user.getId(), user.getDept().getId(), user.getUsername(),
                SecurityConstants.BCRYPT + user.getPassword(), user.getPhone(), true, true, true,
                StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), authorities);
    }

    /**
     * 通过用户实体查询
     *
     * @param user user
     * @return
     */
    default UserDetails loadUserByUser(LzhphantomUser user) {
        return this.loadUserByUsername(user.getUsername());
    }
}
