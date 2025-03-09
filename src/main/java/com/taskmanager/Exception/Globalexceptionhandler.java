package com.taskmanager.Exception;

import com.taskmanager.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;
@ControllerAdvice
public class Globalexceptionhandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handalesnexception(NotFoundException s, WebRequest request) {
        ErrorDto error = new ErrorDto(
                s.getMessage(),
                new Date(),
                500,
                request.getDescription(false),
                Arrays.toString(s.getStackTrace())  // Properly formatting stack trace
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handaaleglobalexception(Exception e, WebRequest request){
        ErrorDto error = new ErrorDto(
                e.getMessage(),
                new Date(),
                500,
                request.getDescription(false),
                Arrays.toString(e.getStackTrace())  // Properly formatting stack trace
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
