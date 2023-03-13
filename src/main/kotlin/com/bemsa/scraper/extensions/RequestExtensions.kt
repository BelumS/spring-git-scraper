package com.bemsa.scraper.extensions

import com.bemsa.scraper.constants.CACHE_TIME_SECONDS
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

fun cachedGitHeaders(): HttpEntity<String> {
    val headers = HttpHeaders()
    headers.set("Accept", "application/vnd.github+json")
    headers.set("Cache-Control", "public, max-age=$CACHE_TIME_SECONDS, s-maxage=$CACHE_TIME_SECONDS")
    return HttpEntity(headers)
}