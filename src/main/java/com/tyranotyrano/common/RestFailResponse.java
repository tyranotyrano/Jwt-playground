package com.tyranotyrano.common;

import lombok.Getter;

@Getter
public class RestFailResponse {
    private String message;
    private Exception exception;

    public RestFailResponse(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }
}