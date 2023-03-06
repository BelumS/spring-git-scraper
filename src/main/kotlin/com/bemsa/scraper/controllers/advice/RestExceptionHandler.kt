package com.bemsa.scraper.controllers.advice;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.exceptions.GitScraperException;
import mu.KotlinLogging
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class RestExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(value = [DataNotFoundException::class])
    fun handleDataNotFoundException(e: DataNotFoundException): ResponseEntity<ApiResponse> {
        log.error(e.message, e);
        val responseEntity = buildError(e.message!!, HttpStatus.NOT_FOUND);

        log.error("handleDataNotFoundException: {}", responseEntity);
        return responseEntity;
    }

    @ExceptionHandler(value = [GitScraperException::class])
    fun handleGitScraperException(e: GitScraperException): ResponseEntity<ApiResponse> {
        log.error(e.message, e);
        val responseEntity = buildError(e.message!!, HttpStatus.BAD_REQUEST);

        log.error("handleGitScraperException: {}", responseEntity);
        return responseEntity;
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleCatchAllException(e: Exception): ResponseEntity<ApiResponse> {
        log.error(e.message, e);
        val responseEntity = buildError(e.message!!, HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("handleCatchAllException: {}", responseEntity);
        return responseEntity;
    }

    private fun buildError(@NonNull message: String, status: HttpStatus): ResponseEntity<ApiResponse> {
        return ResponseEntity<ApiResponse>(
                 ApiResponse(ApiRequest(status.name, message), null),
                 status
        );
    }
}
