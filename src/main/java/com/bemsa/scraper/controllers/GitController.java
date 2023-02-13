package com.bemsa.scraper.controllers;

import com.bemsa.scraper.models.GitUserUI;
import com.bemsa.scraper.services.GithubService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.version.one}/git")
public class GitController {

    private final GithubService githubService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Get User by Username",
            notes = "Retrieves a User's data by their username",
            httpMethod = "GET",
            response = GitUserUI.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GitUserUI getUserByUsername(@PathVariable String username) {
        log.info("...Searching for {}'s data.", username);
        return new GitUserUI(githubService.combineData(username));
    }
}
