package com.tyranotyrano.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SAMPLE_CODE("샘플 에러 코드입니다.");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
