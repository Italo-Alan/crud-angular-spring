package com.italo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.italo.exception.RecordNotFoundException;

//Recomenda o que fazer com todas as exceções lançadas na aplicação
@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }
}
