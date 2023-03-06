package com.bemsa.scraper.clients.impl;

import com.bemsa.scraper.clients.GithubClient
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class GithubClientImpl(private val restTemplate: RestTemplate) : GithubClient {

//    @Retryable(
//            value = [RestClientException::class],
//            maxAttemptsExpression = "\${retry.maxAttempts}",
//            backoff = @Backoff("\${retry.maxDelay}")
    override fun get(url: String, username: String): ResponseEntity<String> = restTemplate.exchange(url, HttpMethod.GET, httpEntity(), String, username)

    override fun asJson(username: String, url: String, errorMessage: String): String? = this.get(url, username).body

    private fun httpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("Accept", "application/vnd.github+json")
        headers.set("Cache-Control", "public, max-age=60, s-maxage=60")
        return HttpEntity(headers)
    }
}
