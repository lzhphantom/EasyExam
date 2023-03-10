package com.lzhphantom.user.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取请求的Token
        String token = request.getHeader("Authorization");
        //没有直接跳过
        if (ObjectUtils.isEmpty(token))
        {
            chain.doFilter(request,response);
            return;
        }
        //将token中的用户名和权限用户组放入Authentication对象,在之后实现鉴权
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        super.doFilterInternal(request, response, chain);
    }
    //解析token获取用户信息
    private UsernamePasswordAuthenticationToken getAuthentication(String token)
    {
        HashMap<String, Object> tokenInfo = JwtUtils.decode(token);
        if(ObjectUtils.isEmpty(tokenInfo)){
            return null;
        }
        String username = (String) tokenInfo.get("username");
        String[] roles = (String[]) tokenInfo.get("roles");
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(String role:roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new UsernamePasswordAuthenticationToken(username,null,authorities);
    }
}
