package com.lucaslevi.meets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundExcepiton (ResourceNotFoundException ex, WebRequest req){
       ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
       return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalExcepitionHandler(Exception ex, WebRequest req){
        ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
