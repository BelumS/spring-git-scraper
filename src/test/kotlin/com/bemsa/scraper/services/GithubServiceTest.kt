package com.bemsa.scraper.services

import com.bemsa.scraper.clients.GithubClient
import com.bemsa.scraper.constants.DATE_FORMAT
import com.bemsa.scraper.constants.GIT_USER
import com.bemsa.scraper.constants.USER_NAME
import com.bemsa.scraper.exceptions.GitScraperException
import com.bemsa.scraper.models.GitRepo
import com.bemsa.scraper.models.GitUser
import com.bemsa.scraper.services.impl.GithubServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.text.SimpleDateFormat

@ExtendWith(MockKExtension::class)
class GithubServiceTest {

    @SpykBean
//    @MockkBean
    private var objectMapper: ObjectMapper = ObjectMapper()

    @MockkBean
    private lateinit var githubClient: GithubClient

    @InjectMockKs
    private var githubService: GithubServiceImpl = GithubServiceImpl(objectMapper, githubClient)

    @BeforeEach
    fun setup() {
        objectMapper.dateFormat = SimpleDateFormat(DATE_FORMAT)
        objectMapper.registerModule(JavaTimeModule())
    }

    @Test
    fun testGetUserData(){
        //given
        val json = "{}"
        every { githubClient.asJson(any(), any(), any()) } returns json
        every { objectMapper.readValue(json, GitUser::class.java) } returns GIT_USER

        //when
        val user = githubService.getUserData(USER_NAME)

        //then
        assertThat(user).isEqualTo(GIT_USER)
    }

    @Test
    fun testGetUserDataThrowsJsonException() {
        //when
        val json = "{}"
//        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json)
//        when(objectMapper.readValue(json, GitUser.class)).thenThrow(JsonProcessingException.class)

        //then
        assertThatExceptionOfType(GitScraperException::class.java)
            .isThrownBy { -> githubService.getUserData("") }
            .withMessage("")


//        assertThatThrownBy(() -> {
//            //given
//            String json = "{}"
//            when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json)
//            when(objectMapper.readValue(json, GitUser.class)).thenThrow(JsonProcessingException.class)
//
//            //when
//            GitUser data = githubService.getUserData("")
//
//            //then
//            assertThat(data).isNull()
//        }).isInstanceOf(GitScraperException.class)
    }

    @Test
    fun testGetUserDataThrowsException() {
        //when
        val json = "{}"
//        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json)
//        when(objectMapper.readValue(json, GitUser.class)).thenThrow(NullPointerException.class)

        //then
        assertThatExceptionOfType(NullPointerException::class.java)
            .isThrownBy { -> githubService.getUserData("") }
            .withMessage("")

//        assertThatThrownBy(() -> {
//            //given
//            val json = "{}"
//            when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json)
//            when(objectMapper.readValue(json, GitUser.class)).thenThrow(NullPointerException.class)
//
//            //when
//            githubService.getUserData("")
//        }).isInstanceOf(NullPointerException.class)
    }

    @Test
    fun testGetRepoData() {
        //given
        val json = "[{}, {}]"
//        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json)

        //when
        val repos = githubService.getRepoData(USER_NAME)

        //then
        assertThat(repos)
                .isNotEmpty
                .hasSize(2)
                .doesNotHaveDuplicates()
                .hasOnlyElementsOfType(GitRepo::class.java)
    }

    @Test
    fun testGetRepoDataThrowsException() {
        //when
//            when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(RuntimeException.class)

        //then
        assertThatExceptionOfType(NullPointerException::class.java)
            .isThrownBy { -> githubService.getRepoData(USER_NAME) }
            .withMessage("")
//        assertThatThrownBy(() -> {
//            //given
//            when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(RuntimeException.class)
//
//            //when
//            List<GitRepo> repos = githubService.getRepoData(USER_NAME)
//
//            //then
//            assertThat(repos).isNull()
//        }).isInstanceOf(NullPointerException.class)
    }

    @Test
    fun testGetRepoDataThrowsGitScraperException() {
        //when
        //            when(githubClient.asJson(anyString(), anyString(), anyString())).thenThrow(GitScraperException.class)

        //then
        assertThatExceptionOfType(GitScraperException::class.java)
            .isThrownBy { -> githubService.getRepoData(USER_NAME) }
            .withMessage("")

//        assertThatThrownBy(() -> {
//            //given
//            when(githubClient.asJson(anyString(), anyString(), anyString())).thenThrow(GitScraperException.class)
//
//            //when
//            List<GitRepo> repos = githubService.getRepoData(USER_NAME)
//
//            //then
//            assertThat(repos).isNull()
//        }).isInstanceOf(GitScraperException.class)
    }

    @Test
    fun testCombineData() {
        //given
        val json = "{}"
        val jsonArray = "[{}, {}]"
//        when(githubClient.asJson(anyString(), anyString(), anyString())).thenReturn(json).thenReturn(jsonArray)
//        when(objectMapper.readValue(json, GitUser.class)).thenReturn(GIT_USER)

        //when
        val user = githubService.combineData(USER_NAME)

        //then
        assertThat(user).isEqualTo(GIT_USER)
    }
}
