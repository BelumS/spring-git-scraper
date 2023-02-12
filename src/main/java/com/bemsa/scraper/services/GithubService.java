package com.bemsa.scraper.services;


import com.bemsa.scraper.models.GitUser;

public interface GithubService {

    /**
     * Retrieves git user data from the external API.
     * @param username the git username
     * @return the user data
     */
    GitUser getData(String username);
}
