package com.bemsa.scraper.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder("request", "response")
data class ApiResponse(
    private val request: ApiRequest,
    private val payload: Any?
)
