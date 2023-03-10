package com.lzhphantom.core.jjwt.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class JwtUtil {
    public static final Long JWT_TTL = 60 * 60 * 1000L;
    public static final String JWT_KEY = "lzhphantom";

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
    }

    public static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        if (Objects.isNull(ttlMillis)) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = currentTimeMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder().setId(uuid).setSubject(subject)
                .setIssuer("lzhphantom").setIssuedAt(now)
                .signWith(hs256, secretKey)
                .setExpiration(expDate);
    }

    public static String createJWT(String subject) {
        return getJwtBuilder(subject, null, getUUID()).compact();
    }

    public static String createJWT(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
    }

    public static String createJWT(String subject, Long ttlMillis, String id) {
        return getJwtBuilder(subject, ttlMillis, id).compact();
    }
}
