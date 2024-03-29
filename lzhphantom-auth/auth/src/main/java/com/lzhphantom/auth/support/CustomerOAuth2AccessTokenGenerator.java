package com.lzhphantom.auth.support;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

/**
 * @author lzhphantom
 */
public class CustomerOAuth2AccessTokenGenerator implements OAuth2TokenGenerator<OAuth2AccessToken> {
    private OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer;

    @Nullable
    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())
                || !OAuth2TokenFormat.REFERENCE.equals(context
                .getRegisteredClient().getTokenSettings().getAccessTokenFormat())) {
            return null;
        }
        String issuer = null;
        if (Objects.nonNull(context.getAuthorizationServerContext())) {
            issuer = context.getAuthorizationServerContext().getIssuer();
        }
        RegisteredClient registeredClient = context.getRegisteredClient();
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getAccessTokenTimeToLive());
        OAuth2TokenClaimsSet.Builder claimsBuilder = OAuth2TokenClaimsSet.builder();
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder.subject(context.getPrincipal().getName())
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .notBefore(issuedAt)
                .id(UUID.randomUUID().toString());
        if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
            claimsBuilder.claim(OAuth2ParameterNames.SCOPE, context.getAuthorizedScopes());
        }

        if (Objects.nonNull(this.accessTokenCustomizer)) {
            OAuth2TokenClaimsContext.Builder accessTokenContextBuilder = OAuth2TokenClaimsContext.with(claimsBuilder)
                    .registeredClient(context.getRegisteredClient())
                    .principal(context.getPrincipal())
                    .authorizationServerContext(context.getAuthorizationServerContext())
                    .authorizedScopes(context.getAuthorizedScopes())
                    .tokenType(context.getTokenType())
                    .authorizationGrantType(context.getAuthorizationGrantType());
            if (Objects.nonNull(context.getAuthorization())) {
                accessTokenContextBuilder.authorization(context.getAuthorization());
            }
            if (Objects.nonNull(context.getAuthorizationGrant())) {
                accessTokenContextBuilder.authorizationGrant(context.getAuthorizationGrant());
            }
            OAuth2TokenClaimsContext accessTokenContext = accessTokenContextBuilder.build();
            this.accessTokenCustomizer.customize(accessTokenContext);
        }
        OAuth2TokenClaimsSet accessTokenClaimSet = claimsBuilder.build();
        return new CustomerOAuth2AccessTokenGenerator.OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType.BEARER,
                UUID.randomUUID().toString(), accessTokenClaimSet.getIssuedAt(), accessTokenClaimSet.getExpiresAt()
                , context.getAuthorizedScopes(), accessTokenClaimSet.getClaims());
    }

    public void setAccessTokenCustomizer(OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer) {
        Assert.notNull(accessTokenCustomizer, "accessTokenCustomizer cannot be null");
        this.accessTokenCustomizer = accessTokenCustomizer;
    }

    private static final class OAuth2AccessTokenClaims extends OAuth2AccessToken implements ClaimAccessor {
        private final Map<String, Object> claims;

        private OAuth2AccessTokenClaims(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
                                        Set<String> scopes, Map<String, Object> claims) {
            super(tokenType, tokenValue, issuedAt, expiresAt, scopes);
            this.claims = claims;
        }

        @Override
        public Map<String, Object> getClaims() {
            return this.claims;
        }
    }
}
