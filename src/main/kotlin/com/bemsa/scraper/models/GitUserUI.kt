package com.bemsa.scraper.models

import com.bemsa.scraper.constants.DATE_FORMAT
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.format.DateTimeFormatter

@JsonPropertyOrder("user_name", "display_name", "avatar", "geo_location", "email", "url", "created_at", "repos")
data class GitUserUI(private val gitUser: GitUser) {
    @JsonProperty("user_name")
    val username = gitUser.login

    @JsonProperty("display_name")
    val displayName = gitUser.name

    val avatar = gitUser.avatarUrl

    @JsonProperty("geo_location")
    val geoLocation = gitUser.location

    val email = gitUser.email

    val url = gitUser.url

    @JsonProperty("created_at")
    val createdAt: String = gitUser.createdAt.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

    val repos: List<GitRepo> = gitUser.repos
}
