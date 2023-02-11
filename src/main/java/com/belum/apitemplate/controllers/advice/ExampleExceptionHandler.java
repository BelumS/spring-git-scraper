package com.belum.apitemplate.controllers.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.invoke.MethodHandles;

/**
 * Created by bel-sahn on 7/29/19
 */
@RestControllerAdvice
public class ExampleExceptionHandler {
    //region PROPERTIES
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//endregion

//region CONSTRUCTORS
    public ExampleExceptionHandler(){}
//endregion

//region GETTERS/SETTERS
//endregion

//region HELPER METHODS
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse> handleCatchAllException(Exception e) {
        log.error(e.getMessage(), e);
        final ApiResponse error =  new ApiResponse(new ApiRequest(null, e.getMessage()), null);
        final ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("handle catchAllException: {}", responseEntity);
        return responseEntity;
    }
//endregion
}
