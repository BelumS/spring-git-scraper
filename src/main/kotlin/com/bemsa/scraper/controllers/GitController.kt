package com.bemsa.scraper.controllers;

import com.bemsa.scraper.models.GitUserUI;
import com.bemsa.scraper.services.GithubService;
import io.swagger.annotations.ApiOperation;
import mu.KotlinLogging
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("\${api.version.one}/git")
class GitController(private val githubService: GithubService) {
    private val log = KotlinLogging.logger {}

    @GetMapping(value = ["/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
            value = "Get User by Username",
            notes = "Retrieves a User's data by their username",
            httpMethod = "GET",
            response = GitUserUI::class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    fun getUserByUsername(@PathVariable username: String): GitUserUI {
        log.info("...Searching for {}'s data.", username);
        return GitUserUI(githubService.combineData(username));
    }
}
