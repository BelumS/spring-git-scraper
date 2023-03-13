package com.bemsa.scraper.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class GitScraperException(override val message: String) : RuntimeException(message)
