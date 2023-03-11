package com.lzhphantom.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.CommonConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author lzhphantom
 * <p>
 * 客户端异常处理 AuthenticationException 不同细化异常处理
 */
@RequiredArgsConstructor
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(CommonConstants.UTF8);
        response.setContentType(CommonConstants.CONTENT_TYPE);
        LzhphantomResult<String> result = new LzhphantomResult<>();
        result.setCode(CommonConstants.FAIL);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (Objects.nonNull(authException)) {
            result.setMsg("error");
            result.setData(authException.getMessage());
        }
        // 针对令牌过期返回特殊的 424
        if (authException instanceof InvalidBearerTokenException
                || authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
            result.setMsg(this.messageSource.getMessage("OAuth2ResourceOwnerBaseAuthenticationProvider.tokenExpired",
                    null, LocaleContextHolder.getLocale()));
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
