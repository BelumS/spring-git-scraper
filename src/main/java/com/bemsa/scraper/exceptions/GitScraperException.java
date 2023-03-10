package com.bemsa.scraper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GitScraperException extends RuntimeException {

    public GitScraperException(String message) {
        super(message);
    }

    public GitScraperException(String message, Throwable cause) {
        super(message, cause);
    }
}
