package com.lzhphantom.auth.config;

import com.lzhphantom.auth.support.core.FormIdentityLoginConfigurer;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 服务安全相关配置
 *
 * @author lzhphantom
 */
@EnableWebSecurity
public class WebSecurityConfiguration {

    /**
     * spring security 默认的安全策略
     * @param http security注入点
     * @return SecurityFilterChain
     * @throws Exception
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests(request-> {
            try {
                request.requestMatchers("/token/*")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                        .and().headers()
                        .frameOptions()
                        .sameOrigin()
                        .and()
                        .apply(new FormIdentityLoginConfigurer());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        http.authenticationProvider(null);
        return http.build();
    }
    /**
     * 暴露静态资源
     *
     * https://github.com/spring-projects/spring-security/issues/10938
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http.securityMatchers((matchers) -> matchers.requestMatchers("/actuator/**", "/css/**", "/error"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache()
                .disable()
                .securityContext()
                .disable()
                .sessionManagement()
                .disable();
        return http.build();
    }

}
