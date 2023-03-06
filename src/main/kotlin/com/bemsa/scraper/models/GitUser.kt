package com.bemsa.scraper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.lang.NonNull

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitUser (
    @NonNull val login: String,
    @NonNull val name: String,
    @NonNull @JsonProperty("avatar_url") val avatarUrl: String,
    @NonNull val location: String,
    val email: String?,
    @NonNull @JsonProperty("html_url") val url: String,
    @NonNull @JsonProperty("created_at") val createdAt: ZonedDateTime,
    @NonNull var repos: MutableList<GitRepo>
) {}
