package com.bemsa.scraper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class GitScraperException(message: String) : RuntimeException(message) {

//    constructor(message: String, cause: Throwable) : this(message) {}
}
