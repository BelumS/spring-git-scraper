package com.bemsa.scraper.controllers.advice

import com.bemsa.scraper.exceptions.DataNotFoundException
import com.bemsa.scraper.exceptions.GitScraperException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.invoke.MethodHandles

@RestControllerAdvice
class RestExceptionHandler {
    private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @ExceptionHandler(value = [DataNotFoundException::class])
    fun handleDataNotFoundException(e: DataNotFoundException): ResponseEntity<ApiResponse> {
        log.error(e.message, e)
        val responseEntity = buildError(e.message, HttpStatus.NOT_FOUND)

        log.error("handleDataNotFoundException: {}", responseEntity)
        return responseEntity
    }

    @ExceptionHandler(value = [GitScraperException::class])
    fun handleGitScraperException(e: GitScraperException): ResponseEntity<ApiResponse> {
        log.error(e.message, e)
        val responseEntity = buildError(e.message, HttpStatus.BAD_REQUEST)

        log.error("handleGitScraperException: {}", responseEntity)
        return responseEntity
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleCatchAllException(e: Exception): ResponseEntity<ApiResponse> {
        log.error(e.message, e)
        val responseEntity = buildError(e.message!!, HttpStatus.INTERNAL_SERVER_ERROR)

        log.error("handleCatchAllException: {}", responseEntity)
        return responseEntity
    }

    private fun buildError(message: String, status: HttpStatus): ResponseEntity<ApiResponse> = ResponseEntity<ApiResponse>(
                 ApiResponse(ApiRequest(status.name, message), null),
                 status
        )
}
