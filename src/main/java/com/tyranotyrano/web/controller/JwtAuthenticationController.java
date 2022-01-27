package com.tyranotyrano.web.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.web.service.JwtUserDetailsService;

@RestController
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtUserDetailsService userDetailService) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }
}
