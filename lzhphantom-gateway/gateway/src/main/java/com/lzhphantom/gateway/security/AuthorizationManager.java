package com.lzhphantom.gateway.security;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 鉴权管理
 */
@Component
@Log4j2
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication.map(auth -> {
            String path = object.getExchange().getRequest().getURI().getPath();
            for (GrantedAuthority authority : auth.getAuthorities()) {
                if (StringUtils.equals(authority.getAuthority(), "ROLE_USER") && path.contains("/user/normal")) {
                    return new AuthorizationDecision(true);
                } else if (StringUtils.equals(authority.getAuthority(), "ROLE_ADMIN") && path.contains("/user/admin")) {
                    return new AuthorizationDecision(true);
                }
            }
            return new AuthorizationDecision(false);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }
}
