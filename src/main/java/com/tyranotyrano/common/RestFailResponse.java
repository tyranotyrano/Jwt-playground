package com.tyranotyrano.common;

import lombok.Getter;

@Getter
public class RestFailResponse {
    private String code;
    private String message;
    private Exception exception;

    public RestFailResponse(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    public RestFailResponse(String code, String message, Exception exception) {
        this.code = code;
        this.message = message;
        this.exception = exception;
    }
}