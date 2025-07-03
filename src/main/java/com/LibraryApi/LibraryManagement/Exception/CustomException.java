package com.LibraryApi.LibraryManagement.Exception;

import org.springframework.http.HttpStatus;

public class CustomException extends  RuntimeException{
    private final HttpStatus status;
    public CustomException(String Message,HttpStatus status){
        super(Message);
        this.status=status;

    }

    public HttpStatus getStatus() {
        return status;
    }
}
