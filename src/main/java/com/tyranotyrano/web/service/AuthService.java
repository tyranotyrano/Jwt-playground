package com.tyranotyrano.web.service;

import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.domain.token.TokenDto;
import com.tyranotyrano.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private static final String COMMA = ",";

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenDto authorize(String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject()
                                                                    .authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        return tokenProvider.createToken(authentication.getName(), authorities);
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                             .map(GrantedAuthority::getAuthority)
                             .collect(Collectors.joining(COMMA));
    }
}
