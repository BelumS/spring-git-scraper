package com.bemsa.scraper.controllers.advice

import com.bemsa.scraper.exceptions.DataNotFoundException
import com.bemsa.scraper.exceptions.GitScraperException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class RestExceptionHandlerTest {
    private val handler = RestExceptionHandler()

    @Test
    fun testHandleDataNotFoundException() {
        //when
        val errorResponse = handler.handleDataNotFoundException(DataNotFoundException("Data not found."))

        //then
        asserts(errorResponse, ResponseEntity.notFound().build())
    }

    @Test
    fun testHandleGitScraperException() {
        //when
        val errorResponse = handler.handleGitScraperException(GitScraperException("Bad request was sent."))

        //then
        asserts(errorResponse, ResponseEntity.badRequest().build())
    }

    @Test
    fun testHandleCatchAllException() {
        //when
        val errorResponse = handler.handleCatchAllException(RuntimeException("An error has occurred!"))

        //then
        asserts(errorResponse, ResponseEntity.internalServerError().build())
    }

    private fun asserts(responseEntity: ResponseEntity<ApiResponse> , error: ResponseEntity<String>) {
        assertThat(responseEntity)
                .isNotNull
                .isExactlyInstanceOf(ResponseEntity::class.java)
                .isNotEqualTo(error)
    }
}
