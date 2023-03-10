package com.lzhphantom.gateway.config;

import com.google.common.collect.Lists;
import com.lzhphantom.gateway.filter.CookieToHeadersFilter;
import com.lzhphantom.gateway.security.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@Configuration
@Log4j2
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private CookieToHeadersFilter cookieToHeadersFilter;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    private final String[] path = {"/user/**"};

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.addFilterBefore(cookieToHeadersFilter, SecurityWebFiltersOrder.HTTP_HEADERS_WRITER);
        //SecurityWebFiltersOrder枚举定义了执行次序
        http.authorizeExchange(exchange -> {
                    exchange.pathMatchers(path).permitAll()
                            .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                    .and().authorizeExchange().pathMatchers("/user/normal/**").hasRole("ROLE_USER")
//                    .and().authorizeExchange().pathMatchers("/user/admin/**").hasRole("ROLE_ADMIN")
//                    也可以这样写，将匹配路径和角色权限写在一起
                            .anyExchange().access(authorizationManager);
                }).httpBasic().and()
                .formLogin().loginPage("/user/login")//登录接口
                .authenticationSuccessHandler(authenticationSuccessHandler)//认证成功
                .authenticationFailureHandler(authenticationFailHandler)//登录验证失败
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)//基于http的接口请求鉴权失败
                .and().csrf().disable()//必须支持跨域
                .logout().logoutUrl("/user/logout")
                .logoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler);
        http.securityContextRepository(securityRepository);
//        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());无状态 默认使用websession
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = Lists.newLinkedList();
        managers.add(authentication -> {
            return Mono.empty();
        });
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(securityUserDetailsService));
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
