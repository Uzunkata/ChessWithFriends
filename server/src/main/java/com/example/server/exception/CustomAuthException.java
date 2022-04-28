package com.example.server.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthException extends AuthenticationException {

    public CustomAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomAuthException(String msg) {
        super(msg);
    }
}
