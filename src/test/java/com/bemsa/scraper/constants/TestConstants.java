package com.bemsa.scraper.constants;

import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.models.GitUserUI;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public final class TestConstants {
    private TestConstants(){}

    public static final String USER_NAME = "test";

    public static final List<GitRepo> GIT_REPO_LIST = List.of(
            new GitRepo("test-repo", "https://github.com/test/test-repo"),
            new GitRepo("spring-boot-poc", "https://github.com/test/spring-boot-poc")
    );

    public static final GitUser GIT_USER = GitUser.builder()
            .login(USER_NAME)
            .name("The Tester")
            .email(null)
            .avatarUrl("https://avatars.githubusercontent.com/u/012345?v=1")
            .location("US")
            .url("https://github.com/test")
            .createdAt(ZonedDateTime.of(2023, 2, 12, 8, 53, 12, 0, ZoneId.systemDefault()))
            .repos(GIT_REPO_LIST)
            .build();

    public static final GitUserUI GIT_USER_UI = new GitUserUI(GIT_USER);
}
