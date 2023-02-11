package com.belum.apitemplate.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@JsonPropertyOrder({"code", "message", "timeStamp"})
@Getter
@ToString
public final class ApiRequest {
    private final String code;
    private final String message;
    private final String timeStamp;

    public ApiRequest(String code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }
}
