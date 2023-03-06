package com.bemsa.scraper.services;


import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;

interface GithubService {

    /**
     * Retrieves git user data from the external API.
     * @param username the git username
     * @return the user data
     */
    fun getUserData(username: String): GitUser

    /**
     * Retrieves git user's repo data from the external API.
     * @param username the git username
     * @return the user's repo data
     */
    fun getRepoData(username: String): List<GitRepo>

    /**
     * Combines the user and repo data into 1 object.
     * @param username the git username
     * @return the git user
     */
    fun combineData(username: String): GitUser
}
