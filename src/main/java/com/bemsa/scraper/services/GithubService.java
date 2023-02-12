package com.bemsa.scraper.services;


import com.bemsa.scraper.models.GitRepo;
import com.bemsa.scraper.models.GitUser;

import java.util.List;

public interface GithubService {

    /**
     * Retrieves git user data from the external API.
     * @param username the git username
     * @return the user data
     */
    GitUser getUserData(String username);

    /**
     * Retrieves git user's repo data from the external API.
     * @param username the git username
     * @return the user's repo data
     */
    List<GitRepo> getRepoData(String username);

    /**
     * Combines the user and repo data into 1 object.
     * @param username the git username
     * @return the git user
     */
    GitUser combineData(String username);
}
