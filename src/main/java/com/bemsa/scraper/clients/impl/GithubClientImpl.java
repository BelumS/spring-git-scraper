package com.bemsa.scraper.clients.impl;

import com.bemsa.scraper.clients.GithubClient;
import com.bemsa.scraper.exceptions.GitScraperException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class GithubClientImpl implements GithubClient {

    private final RestTemplate restTemplate;

    @Override
    @Retryable(
            value = RestClientException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public ResponseEntity<String> get(String url, String username) {
        try {
            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity(),
                    String.class,
                    username
            );
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            throw new GitScraperException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String asJson(String username, String url, String errorMessage) {
        return Optional.of(get(url, username))
                .orElseThrow(() -> new GitScraperException(String.format(errorMessage, username)))
                .getBody();
    }

    private HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Cache-Control", "public, max-age=60, s-maxage=60");
        return new HttpEntity<>(headers);
    }
}
