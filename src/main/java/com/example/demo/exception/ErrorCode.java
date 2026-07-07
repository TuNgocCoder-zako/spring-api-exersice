package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    IVALID_MESSAGE(1001,"Invalid message code"),
    USER_EXIT(1002,"User existed"),
    UNCAGORIZED_EXCEPTION(9999,"uncagorized error"),
    USERNAME_INVALID(1003,"username isn't invalid (at least 3 characters)"),
    PASSWORD_INVALID(1004,"password isn't invalid (at least 8 characters)"),
    ;
    private int code;
    private String message;
}
