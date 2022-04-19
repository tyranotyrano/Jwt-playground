package com.tyranotyrano.web.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tyranotyrano.common.RestFailResponse;
import com.tyranotyrano.exception.RestException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RestException.class)
    public RestFailResponse handleRestException(RestException exception) {
        return new RestFailResponse(exception.getCode(), exception.getMessage(), exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public RestFailResponse handleRuntimeException(RuntimeException exception) {
        return new RestFailResponse(exception.getMessage(), exception);
    }
}

