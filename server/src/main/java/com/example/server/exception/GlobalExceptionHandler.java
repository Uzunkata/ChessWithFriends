package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAllOtherErrors(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CustumAuthException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAllOtherErrors(CustumAuthException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Invalid password or email!");
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAllOtherErrors(CustomException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }
    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseBody
    public ResponseEntity<Object> handleAllOtherErrors(UserAlreadyExists exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }

}
