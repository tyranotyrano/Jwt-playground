package com.tyranotyrano.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/simple")
@RequiredArgsConstructor
public class SimpleController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(name = "value", defaultValue = "hello") String value) {
        System.out.println("value : " + value);
        return ResponseEntity.ok(value);
    }
}
