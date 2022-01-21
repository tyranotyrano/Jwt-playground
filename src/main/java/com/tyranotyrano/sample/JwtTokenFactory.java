package com.tyranotyrano.sample;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class JwtTokenFactory {
    private static final String SUBJECT = "SUBJECT_KEY";
    private static int SESSION_TIME = 60;

    public static String createToken(User user) {
        Date expireDate = new Date(System.currentTimeMillis() + SESSION_TIME * 1000);

        return Jwts.builder()
                   .setSubject(SUBJECT)
                   .setExpiration(expireDate)
                   .claim("user", user)
                   .signWith(getExistedKey())
                   .compact();
    }

    private static SecretKey createNewKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String source = Encoders.BASE64.encode(key.getEncoded());

        return Keys.hmacShaKeyFor(source.getBytes());
    }

    private static SecretKey getExistedKey() {
        // 최소 256 비트 길이의 Key 를 제공해야하므로, 최소 32자 이상을 Key Source 로 사용해야함
        String source = "sample existed key by tyranotyrano";
        return Keys.hmacShaKeyFor(source.getBytes());
    }
}
