package com.bemsa.scraper.controllers;

import com.bemsa.scraper.constants.ApiConstants;
import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.exceptions.GitScraperException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.models.GitUserUI;
import com.bemsa.scraper.services.GithubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = {GitController.class, GithubService.class})
@WebMvcTest
@ExtendWith(MockitoExtension.class)
class GitControllerIT {
    private static final String USER_API = "/api/v1/git" + ApiConstants.USER_API;

    private static final String USER_NAME = "test";

    private static final List<GitRepo> GIT_REPO_LIST = List.of(
            new GitRepo("test-repo", "https://github.com/test/test-repo"),
            new GitRepo("spring-boot-poc", "https://github.com/test/spring-boot-poc")
    );

    private static final GitUser GIT_USER = GitUser.builder()
            .login(USER_NAME)
            .name("The Tester")
            .email(null)
            .avatarUrl("https://avatars.githubusercontent.com/u/012345?v=1")
            .location("US")
            .url("https://github.com/test")
            .createdAt(ZonedDateTime.of(2023, 2, 12, 8, 53, 12, 0, ZoneId.systemDefault()))
            .repos(GIT_REPO_LIST)
            .build();

    private static final GitUserUI GIT_USER_UI = new GitUserUI(GIT_USER);

    static Stream<Arguments> getStreamBadUsernames() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("fail")
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @Test
    void testGetUserByUsername() throws Exception {
        Mockito.when(githubService.getData(USER_NAME)).thenReturn(GIT_USER);
        mockMvc.perform(get(USER_API, USER_NAME).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_name").value(is(GIT_USER_UI.getUsername())))
                .andExpect(jsonPath("$.display_name").value(is(GIT_USER_UI.getDisplayName())))
                .andExpect(jsonPath("$.geo_location").value(is(GIT_USER_UI.getGeoLocation())))
                .andExpect(jsonPath("$.email").value(is(GIT_USER_UI.getEmail())))
                .andExpect(jsonPath("$.url").value(is(GIT_USER_UI.getUrl())))
                .andExpect(jsonPath("$.avatar").value(is(GIT_USER_UI.getAvatar())))
                .andExpect(jsonPath("$.created_at").value(is(GIT_USER_UI.getCreatedAt())))
                .andExpect(jsonPath("$.repos[0].name").value(is(GIT_REPO_LIST.get(0).getName())))
                .andExpect(jsonPath("$.repos[0].url").value(is(GIT_REPO_LIST.get(0).getUrl())))
                .andExpect(jsonPath("$.repos[1].name").value(is(GIT_REPO_LIST.get(1).getName())))
                .andExpect(jsonPath("$.repos[1].url").value(is(GIT_REPO_LIST.get(1).getUrl())));
    }

    @Test
    void testGetUserByUsernameReturns400() throws Exception {
        int id = 12345;
        Mockito.when(githubService.getData(String.valueOf(id))).thenThrow(GitScraperException.class);
        mockMvc.perform(get(USER_API ,id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("com.bemsa.scraper.controllers.GitControllerIT#getStreamBadUsernames")
    void testGetUserByUsernameReturns404(String username) throws Exception {
        Mockito.when(githubService.getData(username)).thenThrow(DataNotFoundException.class);
        mockMvc.perform(get(USER_API ,"/" + username).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
