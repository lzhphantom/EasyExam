package com.lzhphantom.user.config;

import com.lzhphantom.user.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/").permitAll()
                    .requestMatchers("/user").hasRole("user");
        }).formLogin();

        return http.build();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/root/doLogin");
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setAuthenticationManager(authenticationManager);
//        loginFilter.setAuthenticationSuccessHandler(new SuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new FailHandler());
        return loginFilter;
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(AuthenticationManager authenticationManager){
        JWTAuthorizationFilter filter = new JWTAuthorizationFilter(authenticationManager);
        return filter;
    }
    //springsecurity跨域
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
