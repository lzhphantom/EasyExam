package com.lzhphantom.auth.support.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.lzhphantom.core.common.util.LzhphantomWebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @author lzhphantom
 * <p>
 * 表单登录失败处理逻辑
 */
@Log4j2
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("表单登录失败:{}", exception.getLocalizedMessage());
        String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()),
                CharsetUtil.CHARSET_UTF_8);
        LzhphantomWebUtils.getResponse().sendRedirect(url);
    }
}
