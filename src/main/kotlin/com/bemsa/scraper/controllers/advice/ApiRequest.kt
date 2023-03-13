package com.bemsa.scraper.controllers.advice

import com.fasterxml.jackson.annotation.JsonPropertyOrder

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@JsonPropertyOrder("code", "message", "timeStamp")
data class ApiRequest(
    private val code: String,
    private val message: String,
    private val timeStamp: String
) {
    constructor(code: String, message: String) : this(code, message, ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
}
