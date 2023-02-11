package com.belum.apitemplate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends GitScraperException {

    public DataNotFoundException(String message){
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
