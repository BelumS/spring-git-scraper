package com.belum.apitemplate.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@JsonPropertyOrder({"request", "response"})
@Getter
@ToString
@RequiredArgsConstructor
public final class ApiResponse {
    private final ApiRequest request;
    private final Object payload;
}
