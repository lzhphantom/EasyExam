package com.lzhphantom.user.handler;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

@Data
public class SecurityUserDetails extends User implements Serializable {
    private Long userId;
    public SecurityUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,Long userId){
        super(username,password,authorities);
        this.userId = userId;
    }

    public SecurityUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }
}
