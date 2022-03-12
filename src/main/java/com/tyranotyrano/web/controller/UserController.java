package com.tyranotyrano.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.domain.user.User;
import com.tyranotyrano.web.rqrs.SignUpDto;
import com.tyranotyrano.web.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Long> signUp(@RequestBody SignUpDto signUpDto) {
        User user = userService.signup(signUpDto);
        return ResponseEntity.ok(user.getId());
    }
}
