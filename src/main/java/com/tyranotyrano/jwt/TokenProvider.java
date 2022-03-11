package com.tyranotyrano.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.tyranotyrano.domain.token.TokenDto;
import com.tyranotyrano.domain.user.User;
import com.tyranotyrano.domain.user.repository.UserRepository;
import com.tyranotyrano.exception.UnauthorizedException;
import com.tyranotyrano.exception.UserNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "ROLES";
    private static final String EMAIL_CLAIM_KEY = "email";
    private static final String COMMA = ",";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Value("${jwt.access-token-validity-in-millisecond}")
    long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token-validity-in-millisecond}")
    long refreshTokenValidityInMilliseconds;

    @Value("${jwt.secret}")
    private String secret;

    public TokenDto createToken(String email, String authorities) {
        long now = (new Date()).getTime();
        Key secretKey = createSecretKey();

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new UserNotFoundException("해당하는 이메일이 존재하지 않습니다."));

        String accessToken = Jwts.builder()
                                 .claim(EMAIL_CLAIM_KEY, user.getEmail())
                                 .claim(AUTHORITIES_KEY, authorities)
                                 .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                                 .signWith(secretKey, SignatureAlgorithm.HS512)
                                 .compact();

        String refreshToken = Jwts.builder()
                                  .claim(AUTHORITIES_KEY, authorities)
                                  .claim(EMAIL_CLAIM_KEY, user.getEmail())
                                  .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                                  .signWith(secretKey, SignatureAlgorithm.HS512)
                                  .compact();

        return TokenDto.of(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY)
                                                                 .toString()
                                                                 .split(COMMA))
                                                   .map(SimpleGrantedAuthority::new)
                                                   .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.get(EMAIL_CLAIM_KEY), null, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(createSecretKey()).build().parseClaimsJws(token);
            logger.info("validate 들어옴");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch (UnauthorizedException e) {
            logger.info("이미 탈퇴한 회원입니다.");
        }

        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(createSecretKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Key createSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
