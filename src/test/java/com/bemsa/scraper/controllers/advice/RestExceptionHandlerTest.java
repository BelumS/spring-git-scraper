package com.bemsa.scraper.controllers.advice;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.exceptions.GitScraperException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class RestExceptionHandlerTest {
    private final RestExceptionHandler handler = new RestExceptionHandler();

    @Test
    void testHandleDataNotFoundException() {
        //when
        var errorResponse = handler.handleDataNotFoundException(new DataNotFoundException(""));

        //then
        asserts(errorResponse);
    }

    @Test
    void testHandleGitScraperException() {
        //when
        var errorResponse = handler.handleGitScraperException(new GitScraperException(""));

        //then
        asserts(errorResponse);
    }

    @Test
    void testHandleCatchAllException() {
        //when
        var errorResponse = handler.handleCatchAllException(new RuntimeException(""));

        //then
        asserts(errorResponse);
    }

    private void asserts(ResponseEntity<ApiResponse> responseEntity) {
        Assertions.assertThat(responseEntity)
                .isNotNull()
                .isExactlyInstanceOf(ResponseEntity.class)
                .isNotEqualTo(ResponseEntity.ok().build());
    }
}
