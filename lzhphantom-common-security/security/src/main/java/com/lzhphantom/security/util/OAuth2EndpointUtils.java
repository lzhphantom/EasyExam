package com.lzhphantom.security.util;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * @author lzhphantom
 * @description OAuth2 端点工具
 */
@UtilityClass
public class OAuth2EndpointUtils {
    public final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";


    public MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, val) -> {
            if (val.length > 0) {
                for (String s : val) {
                    parameters.add(key, s);
                }
            }
        });
        return parameters;
    }

    public boolean matchesPkceTokenRequest(HttpServletRequest request) {
        return AuthorizationGrantType.AUTHORIZATION_CODE.getValue()
                .equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
                && Objects.nonNull(request.getParameter(OAuth2ParameterNames.CODE))
                && Objects.nonNull(request.getParameter(PkceParameterNames.CODE_VERIFIER));
    }

    public void throwError(String errCode, String parameterName, String errUrl) {
        OAuth2Error error = new OAuth2Error(errCode, "OAuth 2.0 Parameter: " + parameterName, errUrl);
        throw new OAuth2AuthenticationException(error);
    }

    /**
     * 格式化输出token 信息
     *
     * @param authentication 用户认证信息
     * @param claims         扩展信息
     * @return
     */
    public OAuth2AccessTokenResponse sendAccessTokenResponse(OAuth2Authorization authentication,
                                                             Map<String, Object> claims) {
        OAuth2AccessToken accessToken = authentication.getAccessToken().getToken();
        OAuth2RefreshToken refreshToken = authentication.getRefreshToken().getToken();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType())
                .scopes(accessToken.getScopes());
        if (Objects.nonNull(accessToken.getIssuedAt()) && Objects.nonNull(accessToken.getExpiresAt())) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (Objects.nonNull(refreshToken)) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (MapUtil.isNotEmpty(claims)) {
            builder.additionalParameters(claims);
        }
        return builder.build();
    }

}
