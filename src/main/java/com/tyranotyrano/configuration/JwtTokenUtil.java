package com.tyranotyrano.configuration;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token){
        try{
            return getClaimFromToken(token, Claims::getSubject);
        }catch(Exception ex){
            throw new IllegalArgumentException("username from token exception");
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
