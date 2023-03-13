package com.bemsa.scraper.services.impl

import com.bemsa.scraper.clients.GithubClient
import com.bemsa.scraper.constants.USER_API
import com.bemsa.scraper.models.GitRepo
import com.bemsa.scraper.models.GitUser
import com.bemsa.scraper.services.GithubService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.lang.NonNull
import org.springframework.stereotype.Service
import java.lang.invoke.MethodHandles

@Service
class GithubServiceImpl(
    private val mapper: ObjectMapper,
    private val githubClient: GithubClient
): GithubService {
    private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @Cacheable("users")
    override fun getUserData(@NonNull username: String): GitUser {
        val userJson: String? = githubClient.asJson(username, USER_API,"User %s not found")
        val user: GitUser = mapper.readValue(userJson!!)
        log.info("Found data for user: ${user.login}.")
        return user
    }

    @Cacheable("repos")
    override fun getRepoData(username: String): MutableList<GitRepo> {
       val repoJson: String? = githubClient.asJson(username, "$USER_API/repos","No repos found for user: %s")
        val list: MutableList<GitRepo> = mapper.readValue(repoJson!!)
        log.info("Found ${list.size} repos for user: $username.")
        return list
    }

    override fun combineData(username: String): GitUser {
        val user = this.getUserData(username)
        user.repos = this.getRepoData(username)
        return user
    }
}
