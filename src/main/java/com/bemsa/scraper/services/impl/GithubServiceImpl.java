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
import org.springframework.http.ResponseEntity;
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
        headers.set("Cache-Control", "public, max-age=60, s-maxage=60");
        return new HttpEntity<>(headers);
    }

    @Override
    @Cacheable(value = "users")
    @Retryable(
            value = DataNotFoundException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public GitUser getUserData(@NonNull String username) {
        try {
            String userJson = getRequestAsJson(username, USER_API, "User %s not found");
            GitUser user = mapper.readValue(userJson, GitUser.class);
            log.info("Found data for user: {}.", user.getLogin());
            return user;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    @Retryable(
            value = DataNotFoundException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Cacheable(value = "repos")
    public List<GitRepo> getRepoData(String username) {
        try {
            String repoJson = getRequestAsJson(username, USER_API + "/repos","No repos found for user: %s");
            List<GitRepo> list = mapper.readValue(repoJson, new TypeReference<>() {});
            log.info("Found {} repos for user: {}.", list.size(), username);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public GitUser combineData(String username) {
        GitUser user = this.getUserData(username);
        List<GitRepo> repos = this.getRepoData(username);
        user.setRepos(repos);
        return user;
    }

    private ResponseEntity<String> getRequest(String url, String username) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity(),
                String.class,
                username
        );
    }

    private String getRequestAsJson(String username, String url, String errorMessage) {
        return Optional.of(getRequest(url, username))
                .orElseThrow(() -> new DataNotFoundException(String.format(errorMessage, username)))
                .getBody();
    }

}
