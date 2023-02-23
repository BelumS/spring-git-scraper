package com.bemsa.scraper.services.impl;

import com.bemsa.scraper.clients.GithubClient;
import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.exceptions.GitScraperException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.GithubService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static com.bemsa.scraper.constants.ApiConstants.USER_API;

@Slf4j
@RequiredArgsConstructor
@Service
public class GithubServiceImpl implements GithubService {

    private final ObjectMapper mapper;
    private final GithubClient githubClient;

    @Override
    @Cacheable(value = "users")
    public GitUser getUserData(@NonNull String username) {
        try {
            String userJson = githubClient.asJson(username, USER_API, "User %s not found");
            GitUser user = mapper.readValue(userJson, GitUser.class);
            log.info("Found data for user: {}.", user.getLogin());
            return user;
        } catch (GitScraperException | RestClientException | JacksonException e) {
            log.error(e.getMessage(), e);
            throw new GitScraperException(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Cacheable(value = "repos")
    public List<GitRepo> getRepoData(String username) {
        try {
            String repoJson = githubClient.asJson(username, USER_API + "/repos","No repos found for user: %s");
            List<GitRepo> list = mapper.readValue(repoJson, new TypeReference<>() {});
            log.info("Found {} repos for user: {}.", list.size(), username);
            return list;
        }  catch (GitScraperException | RestClientException | JacksonException e) {
            log.error(e.getMessage(), e);
            throw new GitScraperException(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public GitUser combineData(String username) {
        GitUser user = this.getUserData(username);
        List<GitRepo> repos = this.getRepoData(username);
        user.setRepos(repos);
        return user;
    }
}
