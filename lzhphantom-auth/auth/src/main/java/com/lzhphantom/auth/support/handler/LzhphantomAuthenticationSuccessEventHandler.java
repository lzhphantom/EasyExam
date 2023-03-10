package com.lzhphantom.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.core.constant.CommonConstants;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.user.login.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author lzhphantom
 */
@Log4j2
public class LzhphantomAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {
    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
        if (MapUtil.isNotEmpty(map)) {
            // ????????????????????????
            User userInfo = (User) map.get(SecurityConstants.DETAILS_USER);
            log.info("?????????{} ????????????", userInfo.getUsername());
            // ?????? race condition
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(accessTokenAuthentication);
            SecurityContextHolder.setContext(context);
            SysLog logVo = SysLogUtils.getSysLog();
            logVo.setTitle("????????????");
            String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
            if (StrUtil.isNotBlank(startTimeStr)) {
                Long startTime = Long.parseLong(startTimeStr);
                Long endTime = System.currentTimeMillis();
                logVo.setTime(endTime - startTime);
            }
            logVo.setCreateBy(userInfo.getName());
            logVo.setUpdateBy(userInfo.getName());
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }

        // ??????token
        sendAccessTokenResponse(request, response, authentication);
    }

    private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType())
                .scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        // ????????? ???????????? context ??????????????????
        SecurityContextHolder.clearContext();
        this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
    }
}
