package com.lzhphantom.gateway.security;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
@Log4j2
public class SecurityUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info(username);
        if (!StringUtils.equalsAny(username, "admin", "user")) {
            throw new UsernameNotFoundException("username error");
        } else {
            Collection<GrantedAuthority> authorities = Lists.newArrayList();
            if (StringUtils.equals(username, "admin")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            if (StringUtils.equals(username, "user")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            SecurityUserDetails securityUserDetails = new SecurityUserDetails(username, "{bvrypt}" + passwordEncoder.encode("123"), authorities, 1L);
            return Mono.just(securityUserDetails);
        }
    }
}
