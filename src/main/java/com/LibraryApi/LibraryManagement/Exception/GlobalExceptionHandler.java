package com.LibraryApi.LibraryManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {

        LocalDateTime now = LocalDateTime.now();

//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Map<String, Object> body = new HashMap<>();
//        body.put("date", now.format(dateFormatter));
//        body.put("time", now.format(timeFormatter));
//        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", true);
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, ex.getStatus());
    }

}
