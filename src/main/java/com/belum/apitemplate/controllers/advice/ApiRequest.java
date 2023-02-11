package com.belum.apitemplate.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by bel-sahn on 7/29/19
 */
@JsonPropertyOrder({"code", "message", "timeStamp"})
@Getter
@ToString
public final class ApiRequest implements Serializable {
//region PROPERTIES
    private String code;
    private String message;
    private long timeStamp;
    private static final long serialVersionUID = 1L;
//endregion

//region CONSTRUCTORS
    public ApiRequest(String code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }
//endregion

//region GETTERS/SETTERS
//endregion

//region HELPER METHODS
//endregion
}
