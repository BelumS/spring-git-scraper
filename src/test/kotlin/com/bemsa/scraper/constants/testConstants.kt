package com.bemsa.scraper.constants;

import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.models.GitUserUI;

import java.time.ZoneId;
import java.time.ZonedDateTime;

const val USER_NAME = "test";
const val TEST_NAME = "The Tester";
const val API_URL = "http://www.api.com/users";
const val ERROR_MESSAGE = "An error occurred while retrieving data for user: ";

val GIT_REPO_LIST = mutableListOf(
    GitRepo("test-repo", "https://github.com/test/test-repo"),
    GitRepo("spring-boot-poc", "https://github.com/test/spring-boot-poc")
)

val GIT_USER = GitUser(
    login = USER_NAME,
    name = TEST_NAME,
    email = null,
    avatarUrl = "https://avatars.githubusercontent.com/u/012345?v=1",
    location = "US",
    url = "https://github.com/test",
    createdAt = ZonedDateTime.of(2023, 2, 12, 8, 53, 12, 0, ZoneId.systemDefault()),
    repos = GIT_REPO_LIST
)

val GIT_USER_NO_REPOS = GitUser(
    login = USER_NAME,
    name = TEST_NAME,
    email = null,
    avatarUrl = "https://avatars.githubusercontent.com/u/543210?v=1",
    location = "US",
    url = "https://github.com/test",
    createdAt = ZonedDateTime.of(2023, 2, 15, 8, 53, 12, 0, ZoneId.systemDefault()),
    repos = mutableListOf()
)

val GIT_USER_UI = GitUserUI(GIT_USER);
