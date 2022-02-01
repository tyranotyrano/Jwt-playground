package com.tyranotyrano.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiResponse {
    private String message;
    private Object data;

    private ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public static ApiResponse of(String message, Object data) {
        return new ApiResponse(message, data);
    }
}
