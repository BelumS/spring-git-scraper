package com.bemsa.scraper.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class DataNotFoundException(override val message: String): GitScraperException(message)
