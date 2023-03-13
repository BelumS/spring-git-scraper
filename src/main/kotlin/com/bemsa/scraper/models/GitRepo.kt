package com.bemsa.scraper.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitRepo(
    val name: String,
    val url: String
)
