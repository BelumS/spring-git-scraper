package com.bemsa.scraper.services;

import com.bemsa.scraper.exceptions.DataNotFoundException;
import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.impl.GithubServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.bemsa.scraper.constants.ApiConstants.DATE_FORMAT;
import static com.bemsa.scraper.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

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
        when(objectMapper.writeValueAsString(any(GitUser.class))).thenReturn(json);
        ResponseEntity<String> userResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_USER));
        when(getResponseEntity()).thenReturn(userResponse);
        when(objectMapper.readValue(json, GitUser.class)).thenReturn(GIT_USER);

        //when
        GitUser user = githubService.getUserData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }

    @Test
    void testGetUserDataThrowsException() {
        assertThatThrownBy(() -> {
            //given
            String json = "{}";
            ResponseEntity<String> userResponse = ResponseEntity.ok(json);
            when(getResponseEntity()).thenReturn(userResponse);
            when(objectMapper.readValue(json, GitUser.class)).thenThrow(DataNotFoundException.class);

            //when
            githubService.getUserData("");
        }).isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testGetRepoData() throws Exception {
        //given
        ResponseEntity<String> repoResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_REPO_LIST));
        when(getResponseEntity()).thenReturn(repoResponse);

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
            when(getResponseEntity()).thenThrow(RestClientException.class);

            //when
            List<GitRepo> repos = githubService.getRepoData(USER_NAME);

            //then
            githubService.getRepoData("");
        }).isInstanceOf(DataNotFoundException.class);
    }

    //Skipped due to test complexity
    @Disabled
    @Test
    void testCombineData() throws Exception {
        //given
        ResponseEntity<String> userResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_USER_NO_REPOS));
        when(getResponseEntity()).thenReturn(userResponse);
        when(Mockito.spy(githubService.getRepoData(USER_NAME))).thenReturn(GIT_REPO_LIST);

        ResponseEntity<String> repoResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_REPO_LIST));
        when(getResponseEntity()).thenReturn(repoResponse);

        //when
        GitUser user = githubService.combineData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }

    private ResponseEntity<String> getResponseEntity() {
        return restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class),
                anyString());
    }
}
