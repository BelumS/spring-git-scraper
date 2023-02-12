package com.bemsa.scraper.services;

import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;
import com.bemsa.scraper.services.impl.GithubServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.bemsa.scraper.constants.ApiConstants.DATE_FORMAT;
import static com.bemsa.scraper.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Captor
    private ArgumentCaptor<Object> argumentCaptor;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubServiceImpl githubService;

    private HttpEntity<String> httpEntity;

    @BeforeEach
    void setup() {
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        objectMapper.registerModule(new JavaTimeModule());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        httpEntity = new HttpEntity<>(headers);
    }

    @Test
    void testGetUserData() throws Exception {
        //given
        String url = "url";
        when(objectMapper.writeValueAsString(any(GitUser.class))).thenReturn("{}");
        ResponseEntity<String> userResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_USER));
        when(getResponseEntity()).thenReturn(userResponse);
//        when(objectMapper.readValue(anyString(), eq(GitUser.class))).thenReturn(GIT_USER);

        //when
        GitUser user = githubService.getUserData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }

    @Test
    void testGetRepoData() throws Exception {
        //given
        when(objectMapper.writeValueAsString(any(List.class))).thenReturn("[{}, {}]");
        ResponseEntity<String> repoResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_REPO_LIST));
        when(getResponseEntity()).thenReturn(repoResponse);
        when(objectMapper.readValue(anyString(), eq(List.class))).thenReturn(GIT_REPO_LIST);

        //when
        List<GitRepo> repos = githubService.getRepoData(USER_NAME);

        //then
        assertThat(repos).isEqualTo(GIT_REPO_LIST);
    }

    @Test
    void testCombineData() throws Exception {
        //given
        when(objectMapper.writeValueAsString(any(List.class))).thenReturn("[{}, {}]");
        ResponseEntity<String> repoResponse = ResponseEntity.ok(objectMapper.writeValueAsString(GIT_REPO_LIST));
        when(getResponseEntity()).thenReturn(repoResponse);
        when(objectMapper.readValue(anyString(), eq(List.class))).thenReturn(GIT_REPO_LIST);

        //when
        GitUser user = githubService.combineData(USER_NAME);

        //then
        assertThat(user).isEqualTo(GIT_USER);
    }

    private ResponseEntity<String> getResponseEntity() {
        return restTemplate.exchange(
                "/{username}",
                HttpMethod.GET,
                httpEntity,
                String.class,
                USER_NAME);
    }
}
