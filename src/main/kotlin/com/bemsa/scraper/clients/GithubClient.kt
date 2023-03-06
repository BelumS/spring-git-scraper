package com.bemsa.scraper.clients;

import org.springframework.http.ResponseEntity;

interface GithubClient {

    /**
     * Sends a GET request for data to the Github API.
     * @param url the request URI
     * @param username the github user's name
     * @return the response
     */
    fun get(url: String, username: String): ResponseEntity<String>

    /**
     * Converts a request to JSON
     * @param username the github user's name
     * @param url the request URI
     * @param errorMessage an error message
     * @return the JSON
     */
    fun asJson(username: String, url: String, errorMessage: String): String?
}
