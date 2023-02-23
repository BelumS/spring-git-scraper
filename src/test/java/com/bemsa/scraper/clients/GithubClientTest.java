package com.bemsa.scraper.clients;

import com.bemsa.scraper.clients.impl.GithubClientImpl;
import com.bemsa.scraper.exceptions.GitScraperException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.bemsa.scraper.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubClientImpl githubClient;

    @DisplayName("GET request returns 200")
    @Test
    void testGet() {
        //given
        String json = "{}";
        ResponseEntity<String> userResponse = ResponseEntity.ok(json);
        when(getResponseEntity()).thenReturn(userResponse);

        //when
        ResponseEntity<String> request = githubClient.get(API_URL, USER_NAME);

        //then
        assertThat(request)
                .isNotNull()
                .isEqualTo(userResponse);
    }

    @DisplayName("GET request returns 4xx")
    @Test
    void testGetThrowsRestClientException() {
        assertThatThrownBy(() -> {
            //given
            when(getResponseEntity()).thenThrow(RestClientException.class);

            //when
            var response = githubClient.get(API_URL, USER_NAME);

            //then
            assertThat(response.getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        }).isInstanceOf(GitScraperException.class);
    }

    @DisplayName("GET request returns 5xx")
    @Test
    void testGetThrowsException() {
        assertThatThrownBy(() -> {
            //given
            when(getResponseEntity()).thenThrow(NullPointerException.class);

            //when
            var response = githubClient.get(API_URL, USER_NAME);

            //then
            assertThat(response).isNull();
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testAsJson() {
        //given
        String json = "{}";
        ResponseEntity<String> userResponse = ResponseEntity.ok(json);
        when(getResponseEntity()).thenReturn(userResponse);

        //when
        String requestJson = githubClient.asJson(USER_NAME, API_URL, ERROR_MESSAGE);

        //then
        assertThat(requestJson)
                .isEqualTo(json);
    }

    @Test
    void testGetRequestAsJsonThrowsException() {
        assertThatThrownBy(() -> {
            //given
            when(getResponseEntity()).thenThrow(RestClientException.class);

            //when
            String requestJson = githubClient.asJson(USER_NAME, API_URL, ERROR_MESSAGE);

            //then
            assertThat(requestJson).isNull();
        }).isInstanceOf(GitScraperException.class);
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
