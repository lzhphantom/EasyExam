package com.lzhphantom.user.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 *失败配置
 */
public class FailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        ResultBody result = null;
//        if (e instanceof AccountExpiredException)
//        {
//            result = ResultBody.error(AliErrorCodeEnum.USER_ERROR_A0230);
//        }else if (e instanceof BadCredentialsException){
//            result = ResultBody.error(AliErrorCodeEnum.USER_ERROR_A0210);
//        }else if (e instanceof CredentialsExpiredException){
//            result = ResultBody.error(AliErrorCodeEnum.USER_ERROR_A0202);
//        }else if (e instanceof InternalAuthenticationServiceException){
//            result = ResultBody.error(AliErrorCodeEnum.USER_ERROR_A0201);
//        }else{
//            result = ResultBody.error(AliErrorCodeEnum.USER_ERROR_A0220);
//        }

        response.setContentType("application/json;charset=UTF-8");
//        String s = new ObjectMapper().writeValueAsString(result);
//        response.getWriter().println(s);
    }
}
