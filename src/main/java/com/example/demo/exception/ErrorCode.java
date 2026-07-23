package com.example.demo.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    IVALID_MESSAGE(1001,"Invalid message code",HttpStatus.BAD_REQUEST),
    USER_EXIT(1002,"User existed",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIT(1005,"User not existed",HttpStatus.NOT_FOUND),
    UNCAGORIZED_EXCEPTION(9999,"uncagorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1003,"username isn't invalid (at least 3 characters)",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"password isn't invalid (at least 8 characters)",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZE(1007,"You don't have permission",HttpStatus.FORBIDDEN),
    ;
     int code;
     String message;
     HttpStatusCode httpStatusCode;
}
