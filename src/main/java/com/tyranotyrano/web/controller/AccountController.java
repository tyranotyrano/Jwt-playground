package com.tyranotyrano.web.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyranotyrano.domain.Account;
import com.tyranotyrano.dto.ApiResponse;
import com.tyranotyrano.dto.RegisterAccountRq;
import com.tyranotyrano.web.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterAccountRq rq){
        accountService.register(rq);
        return new ResponseEntity<>(ApiResponse.of("성공", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<ApiResponse> findAccount(@PathVariable("accountId") Long accountId) {
        Optional<Account> accountOpt = accountService.findById(accountId);
        return new ResponseEntity<>(ApiResponse.of("성공", accountOpt.orElse(null)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{accountId}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteById(accountId);
        return new ResponseEntity<>(ApiResponse.of("성공", HttpStatus.NO_CONTENT), HttpStatus.OK);
    }
}
