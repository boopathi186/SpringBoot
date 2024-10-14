package com.attendance.attendance_management.exceptionhandler;

import com.attendance.attendance_management.configure.SecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Object> handleException(SecurityException e)
    {
        return new ResponseEntity<>("Expired Token", HttpStatus.BAD_REQUEST) ;
    }

}
