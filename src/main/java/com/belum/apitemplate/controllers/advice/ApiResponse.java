package com.belum.apitemplate.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

/**
 * Created by bel-sahn on 7/29/19
 */
@JsonPropertyOrder({"request", "response"})
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class ApiResponse implements Serializable {
//region PROPERTIES
    private ApiRequest request;
    private transient Object payload;
    private static final long serialVersionUID = 1L;
//endregion

//region CONSTRUCTORS
//endregion

//region GETTERS/SETTERS
//endregion

//region HELPER METHODS
//endregion
}
