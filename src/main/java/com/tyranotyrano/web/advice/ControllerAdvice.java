package com.tyranotyrano.web.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tyranotyrano.common.RestFailResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public RestFailResponse handleRuntimeException(RuntimeException exception) {
        return new RestFailResponse(exception.getMessage(), exception);
    }
}

