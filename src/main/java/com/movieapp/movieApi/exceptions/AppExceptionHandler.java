package com.movieapp.movieApi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail movieNotFoundException(RuntimeException runtimeException){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND , runtimeException.getMessage());
    }


}
