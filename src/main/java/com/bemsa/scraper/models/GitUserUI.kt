package com.bemsa.scraper.models;

import com.bemsa.scraper.constants.DATE_FORMAT
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.lang.NonNull

import java.time.format.DateTimeFormatter;

@JsonPropertyOrder("user_name", "display_name", "avatar", "geo_location", "email", "url", "created_at", "repos")
data class GitUserUI(private val gitUser: GitUser) {

    @NonNull
    @JsonProperty("user_name")
    val username = gitUser.login

    @NonNull
    @JsonProperty("display_name")
    val displayName = gitUser.name

    @NonNull
    val avatar = gitUser.avatarUrl

    @NonNull
    @JsonProperty("geo_location")
    val geoLocation = gitUser.location

    val email = gitUser.email

    @NonNull
    val url = gitUser.url

    @NonNull
    @JsonProperty("created_at")
    val createdAt: String = gitUser.createdAt.format(DateTimeFormatter.ofPattern(DATE_FORMAT));

    @NonNull
    val repos: List<GitRepo> = gitUser.repos
}
