package com.tyranotyrano.dto;

import com.tyranotyrano.domain.Account;

import lombok.Getter;

@Getter
public class RegisterAccountRq {
    private String name;
    private String password;

    public Account toDto() {
        return Account.of(name, password);
    }
}
