package com.bemsa.scraper.services.impl;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.GithubService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.bemsa.scraper.constants.ApiConstants.USER_API;

@Slf4j
@RequiredArgsConstructor
@Service
public class GithubServiceImpl implements GithubService {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    private HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        return new HttpEntity<>(headers);
    }

    @Override
    @Cacheable(value = "users")
    @Retryable(
            value = DataNotFoundException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public GitUser getData(@NonNull String username) {
        try {
            String userJson = getUserJson(username);
            GitUser gitUser = mapper.readValue(userJson, GitUser.class);

            String repoJson = getRepoJson(username);
            List<GitRepo> repos = mapper.readValue(repoJson, new TypeReference<>(){});
            gitUser.setRepos(repos);

            return gitUser;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataNotFoundException(e.getMessage(), e);
        }
    }

    private String getRequestBody(String url, String username) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity(),
                String.class,
                username
        )
        .getBody();
    }

    private String getUserJson(String username) {
        return Optional
                .ofNullable(getRequestBody(USER_API, username))
                .orElseThrow(() -> new DataNotFoundException(String.format("User %s not found", username)));
    }

    private String getRepoJson(String username) {
        return Optional
                .ofNullable(getRequestBody(USER_API + "/repos", username))
                .orElseThrow(() -> new DataNotFoundException(String.format("No repos found for user: %s", username)));
    }
}
