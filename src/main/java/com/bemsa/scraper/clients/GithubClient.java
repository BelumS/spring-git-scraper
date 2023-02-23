package com.bemsa.scraper.clients;

import org.springframework.http.ResponseEntity;

public interface GithubClient {

    /**
     * Sends a GET request for data to the Github API.
     * @param url the request URI
     * @param username the github user's name
     * @return the response
     */
    ResponseEntity<String> get(String url, String username);

    /**
     * Converts a request to JSON
     * @param username the github user's name
     * @param url the request URI
     * @param errorMessage an error message
     * @return the JSON
     */
    String asJson(String username, String url, String errorMessage);
}
