package com.bemsa.scraper.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({"request", "response"})
@Getter
@ToString
@RequiredArgsConstructor
public final class ApiResponse {
    private final ApiRequest request;
    private final Object payload;
}
