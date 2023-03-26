package com.lzhphantom.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.core.constant.CommonConstants;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.log.event.LzhphantomLogEvent;
import com.lzhphantom.log.utils.LogTypeEnum;
import com.lzhphantom.log.utils.LogUtils;
import com.lzhphantom.user.login.entity.SystemLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author lzhphantom
 */
@Log4j2
public class LzhphantomAuthenticationFailureEventHandler implements AuthenticationFailureHandler {
    private final MappingJackson2HttpMessageConverter errorHttpResponseConverter = new MappingJackson2HttpMessageConverter();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String username = request.getParameter(OAuth2ParameterNames.USERNAME);

        log.info("用户：{} 登录失败，异常：{}", username, exception.getLocalizedMessage());
        SystemLog logVo = LogUtils.getSystemLog();

        logVo.setTitle("登录失败");
        logVo.setType(LogTypeEnum.ERROR.getType());
        logVo.setException(exception.getLocalizedMessage());
        // 发送异步日志事件
        String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
        if (StrUtil.isNotBlank(startTimeStr)) {
            Long startTime = Long.parseLong(startTimeStr);
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
        }
        logVo.setCreateBy(username);
        logVo.setUpdateBy(username);
        SpringContextHolder.publishEvent(new LzhphantomLogEvent(logVo));
        // 写出错误信息
        sendErrorResponse(request, response, exception);
    }

    @SneakyThrows
    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        String errorMessage;

        if (exception instanceof OAuth2AuthenticationException authorizationException) {
            errorMessage = StrUtil.isBlank(authorizationException.getError().getDescription())
                    ? authorizationException.getError().getErrorCode()
                    : authorizationException.getError().getDescription();
        }
        else {
            errorMessage = exception.getLocalizedMessage();
        }

        // 手机号登录
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (SecurityConstants.APP.equals(grantType)) {
            errorMessage = MsgUtils.getSecurityMessage("AbstractUserDetailsAuthenticationProvider.smsBadCredentials");
        }

        this.errorHttpResponseConverter.write(LzhphantomResult.failed(errorMessage), MediaType.APPLICATION_JSON, httpResponse);
    }
}
