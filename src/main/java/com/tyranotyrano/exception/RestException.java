package com.tyranotyrano.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {
    private String code;

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
