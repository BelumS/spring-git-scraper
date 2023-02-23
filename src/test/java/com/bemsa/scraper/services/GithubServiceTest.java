package com.bemsa.scraper.services;

import com.bemsa.scraper.clients.GithubClient;
import com.bemsa.scraper.exceptions.GitScraperException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.impl.GithubServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.bemsa.scraper.constants.ApiConstants.DATE_FORMAT;
import static com.bemsa.scraper.constants.TestConstants.GIT_USER;
import static com.bemsa.scraper.constants.TestConstants.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private GithubServiceImpl githubService;

    @BeforeEach
    void setup() {
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetUserData() throws Exception {
        //given
        String json = "{}";
        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json);
        when(objectMapper.readValue(json, GitUser.class)).thenReturn(GIT_USER);

        //when
        GitUser user = githubService.getUserData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }

    @Test
    void testGetUserDataThrowsJsonException() {
        assertThatThrownBy(() -> {
            //given
            String json = "{}";
            when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json);
            when(objectMapper.readValue(json, GitUser.class)).thenThrow(JsonProcessingException.class);

            //when
            GitUser data = githubService.getUserData("");

            //then
            assertThat(data).isNull();
        }).isInstanceOf(GitScraperException.class);
    }

    @Test
    void testGetUserDataThrowsException() {
        assertThatThrownBy(() -> {
            //given
            String json = "{}";
            when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json);
            when(objectMapper.readValue(json, GitUser.class)).thenThrow(NullPointerException.class);

            //when
            githubService.getUserData("");
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGetRepoData() {
        //given
        String json = "[{}, {}]";
        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json);

        //when
        List<GitRepo> repos = githubService.getRepoData(USER_NAME);

        //then
        assertThat(repos)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .hasOnlyElementsOfType(GitRepo.class);
    }

    @Test
    void testGetRepoDataThrowsException() {
        assertThatThrownBy(() -> {
            //given
            when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(RuntimeException.class);

            //when
            List<GitRepo> repos = githubService.getRepoData(USER_NAME);

            //then
            assertThat(repos).isNull();
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGetRepoDataThrowsGitScraperException() {
        assertThatThrownBy(() -> {
            //given
            when(githubClient.asJson(anyString(), anyString(), anyString())).thenThrow(GitScraperException.class);

            //when
            List<GitRepo> repos = githubService.getRepoData(USER_NAME);

            //then
            assertThat(repos).isNull();
        }).isInstanceOf(GitScraperException.class);
    }

    @Test
    void testCombineData() throws Exception {
        //given
        String json = "{}";
        String jsonArray = "[{}, {}]";
        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json).thenReturn(jsonArray);
        when(objectMapper.readValue(json, GitUser.class)).thenReturn(GIT_USER);

        //when
        GitUser user = githubService.combineData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }
}
