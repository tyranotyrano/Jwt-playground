package com.tyranotyrano.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.configuration.JwtTokenUtil;
import com.tyranotyrano.dto.ApiResponse;
import com.tyranotyrano.dto.JwtRequest;
import com.tyranotyrano.web.service.JwtUserDetailsService;

@RestController
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtUserDetailsService userDetailService,
                                       JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/api/v1/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
        ApiResponse response = ApiResponse.of("성공", jwtTokenUtil.generateToken(userDetails));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            throw new DisabledException("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
    }
}
