package com.bemsa.scraper.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitUser (
    val login: String,
    val name: String,
    @JsonProperty("avatar_url") val avatarUrl: String,
    val location: String,
    val email: String?,
    @JsonProperty("html_url") val url: String,
    @JsonProperty("created_at") val createdAt: ZonedDateTime,
    var repos: MutableList<GitRepo>
)
