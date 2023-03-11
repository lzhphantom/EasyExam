package com.lzhphantom.security.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.Map;

/**
 * @author lzhphantom
 * 扩展用户信息
 */
@Data
public class LzhphantomUser extends User implements OAuth2AuthenticatedPrincipal {
    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long id;

    /**
     * 部门ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long deptId;

    /**
     * 手机号
     */
    private final String phone;

    @Override
    public Map<String, Object> getAttributes() {
        return Maps.newHashMap();
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

    public LzhphantomUser(Long id, Long deptId, String username, String password, String phone, boolean enabled,
                          boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.phone = phone;
    }
}
