package com.lzhphantom.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    //登陆认证接口
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //判断是否为Post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //判断认证数据是否为Json 如果登陆数据为JSON 进行JSON处理
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            Map<String, String> loginData = new HashMap<>();
            try {
                //将Json数据转换为Map
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());
            if (username == null) {
                throw new AuthenticationServiceException("用户名不能为空");
            }
            if (password == null) {
                throw new AuthenticationServiceException("密码不能为空");
            }
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            //普通表单提交数据处理
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
}
