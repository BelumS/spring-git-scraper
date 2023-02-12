package com.bemsa.scraper.services.impl;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.GithubService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    @Retryable(
            value = DataNotFoundException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public GitUser getData(@NonNull String username) {
        try {
            String repoJson = getRepoJson(username);
            List<GitRepo> repos = mapper.readValue(repoJson, new TypeReference<>(){});

            String userJson = getUserJson(username);
            JsonNode userNode = mapper.readTree(userJson);
            GitUser gitUser = GitUser.builder()
                    .username(parse(userNode, "login"))
                    .displayName(parse(userNode, "name"))
                    .avatar(parse(userNode, "avatar_url"))
                    .email(parse(userNode, "email"))
                    .geoLocation(parse(userNode, "location"))
                    .repos(repos)
                    .url(parse(userNode, "html_url"))
                    .createdAt(ZonedDateTime.parse(parse(userNode, "created_at"), DateTimeFormatter.ISO_DATE_TIME))
                    .build();

            //TODO: 5. cache the data
            //TODO: 6. return cached data

            return gitUser;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataNotFoundException(e.getMessage(), e);
        }
    }

    private String getRequestBody(@NonNull String url, @NonNull String username) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity(),
                String.class,
                username
        ).getBody();
    }

    private String getUserJson(String username) {
        return Optional
                .ofNullable(getRequestBody("/{username}", username))
                .orElseThrow(() -> new DataNotFoundException(String.format("User %s not found", username)));
    }

    private String getRepoJson(String username) {
        return Optional
                .ofNullable(getRequestBody("/{username}/repos", username))
                .orElseThrow(() -> new DataNotFoundException(String.format("No repos found for user: %s", username)));
    }

    private String parse(JsonNode node, String fieldName) {
        return node.get(fieldName).asText();
    }
}
