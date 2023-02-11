package com.bemsa.scraper.controllers.advice;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.exceptions.GitScraperException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<ApiResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.error(e.getMessage(), e);
        ResponseEntity<ApiResponse> responseEntity = buildError(e.getMessage(), HttpStatus.NOT_FOUND);

        log.error("handleDataNotFoundException: {}", responseEntity);
        return responseEntity;
    }

    @ExceptionHandler(value = {GitScraperException.class})
    public ResponseEntity<ApiResponse> handleGitScraperException(GitScraperException e) {
        log.error(e.getMessage(), e);
        ResponseEntity<ApiResponse> responseEntity = buildError(e.getMessage(), HttpStatus.BAD_REQUEST);

        log.error("handleGitScraperException: {}", responseEntity);
        return responseEntity;
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse> handleCatchAllException(Exception e) {
        log.error(e.getMessage(), e);
        ResponseEntity<ApiResponse> responseEntity = buildError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("handleCatchAllException: {}", responseEntity);
        return responseEntity;
    }

    private ResponseEntity<ApiResponse> buildError(String message, HttpStatus status) {
        return new ResponseEntity<>(
                new ApiResponse(new ApiRequest(null, message), null),
                status
        );
    }
}
