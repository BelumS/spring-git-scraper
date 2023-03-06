package com.bemsa.scraper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitRepo(
    @NonNull val name: String,
    @NonNull val url: String
) {}
