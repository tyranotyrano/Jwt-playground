package com.tyranotyrano.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.domain.token.TokenDto;
import com.tyranotyrano.web.rqrs.LoginDto;
import com.tyranotyrano.web.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 로그인을 했을때 토큰(AccessToken, RefreshToken)을 주는 메서드
     */
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.authorize(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * AccessToken 이 만료되었을 때 토큰(AccessToken , RefreshToken)재발급해주는 메서드
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto requestTokenDto) {
        TokenDto tokenDto = authService.reissue(requestTokenDto.getAccessToken(), requestTokenDto.getRefreshToken());
        return ResponseEntity.ok(tokenDto);
    }
}
