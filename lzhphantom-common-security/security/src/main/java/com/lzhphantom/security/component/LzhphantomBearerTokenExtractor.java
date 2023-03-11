package com.lzhphantom.security.component;

import cn.hutool.core.util.ArrayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lzhphantom
 */
public class LzhphantomBearerTokenExtractor implements BearerTokenResolver {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z\\d-:._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    private boolean allowFormEncodedBodyParameter = false;

    private boolean allowUriQueryParameter = true;

    private final String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final PermitAllUrlProperties urlProperties;

    public LzhphantomBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
        this.urlProperties = urlProperties;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        boolean match = urlProperties.getUrls()
                .stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
        if (match) {
            return null;
        }
        final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        final String parameterToken = isParameterTokenSupportedForRequest(request)
                ? resolveFromRequestParameters(request) : null;
        if (Objects.nonNull(authorizationHeaderToken)) {
            if (Objects.nonNull(parameterToken)) {
                final BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
        }
        if (Objects.nonNull(parameterToken) && isParameterTokenEnabledForRequest(request)) {
            return parameterToken;
        }
        return null;

    }

    private boolean isParameterTokenEnabledForRequest(HttpServletRequest request) {
        return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
    }

    private String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        if (ArrayUtil.isEmpty(values)) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        } else {
            BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
            throw new OAuth2AuthenticationException(error);
        }
    }

    private boolean isParameterTokenSupportedForRequest(HttpServletRequest request) {
        return (("POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || "GET".equals(request.getMethod()));
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }
}
