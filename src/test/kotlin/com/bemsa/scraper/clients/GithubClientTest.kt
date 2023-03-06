package com.bemsa.scraper.clients;

import com.bemsa.scraper.clients.impl.GithubClientImpl
import com.bemsa.scraper.constants.API_URL
import com.bemsa.scraper.constants.USER_NAME
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

//import static com.bemsa.scraper.constants.TestConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubClientTest {
    @MockkBean
    private lateinit var restTemplate: RestTemplate

//    @Autowired
//    private lateinit var restTemplate: TestRestTemplate

    @InjectMockKs
    private lateinit var githubClient: GithubClientImpl

//    private val githubClient = GithubClientImpl(restTemplate)

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @DisplayName("GET request returns 200")
    @Test
    fun testGet() {
        //given
        val json = "{}"
        val userResponse = ResponseEntity.ok(json);
        every { getResponseEntity() } returns userResponse
//        val json = "{}"
//        ResponseEntity<String> userResponse = ResponseEntity.ok(json);
//        when(getResponseEntity(USER_NAME)).thenReturn(userResponse);

        //when
        val request = githubClient.get(API_URL, USER_NAME);

        //then
          verify(exactly = 1) { getResponseEntity()  }
          assertThat(request)
                .isNotNull
                .isEqualTo(userResponse);
    }

    @DisplayName("GET request returns 4xx")
    @Test
    fun testGetThrowsRestClientException() {
//        assertThatThrownBy(() -> {
//            //given
//            when(getResponseEntity()).thenThrow(RestClientException.class);
//
//            //when
//            var response = githubClient.get(API_URL, USER_NAME);
//
//            //then
//            assertThat(response.getStatusCode())
//                    .isEqualTo(HttpStatus.BAD_REQUEST);
//        }).isInstanceOf(GitScraperException.class);
    }

    @DisplayName("GET request returns 5xx")
    @Test
    fun testGetThrowsException() {
//        assertThatThrownBy(() -> {
//            //given
//            when(getResponseEntity()).thenThrow(NullPointerException.class);
//
//            //when
//            var response = githubClient.get(API_URL, USER_NAME);
//
//            //then
//            assertThat(response).isNull();
//        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    fun testAsJson() {
//        //given
//        String json = "{}";
//        ResponseEntity<String> userResponse = ResponseEntity.ok(json);
//        when(getResponseEntity()).thenReturn(userResponse);
//
//        //when
//        String requestJson = githubClient.asJson(USER_NAME, API_URL, ERROR_MESSAGE);
//
//        //then
//        assertThat(requestJson)
//                .isEqualTo(json);
    }

    @Test
    fun testGetRequestAsJsonThrowsException() {
//        assertThatThrownBy(() -> {
//            //given
//            when(getResponseEntity()).thenThrow(RestClientException.class);
//
//            //when
//            String requestJson = githubClient.asJson(USER_NAME, API_URL, ERROR_MESSAGE);
//
//            //then
//            assertThat(requestJson).isNull();
//        }).isInstanceOf(GitScraperException.class);
    }

    private fun getResponseEntity(): ResponseEntity<String> = restTemplate.exchange(API_URL, HttpMethod.GET, getHttpEntity(), String::class.java, USER_NAME)

    private fun getHttpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("Accept", "application/vnd.github+json")
        headers.set("Cache-Control", "public, max-age=60, s-maxage=60")
        return HttpEntity(headers)
    }


//"",
//HttpMethod.GET),
//any(HttpEntity::class),
//eq(String.class),
//anyString());
}
