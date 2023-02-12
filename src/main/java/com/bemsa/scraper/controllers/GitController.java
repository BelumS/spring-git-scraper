package com.bemsa.scraper.controllers;

import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.GithubService;
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
    public GitUser getUserByUsername(@PathVariable String username) {
        return githubService.getData(username);
    }
}
