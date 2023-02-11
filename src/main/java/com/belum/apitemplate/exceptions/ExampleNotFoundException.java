package com.belum.apitemplate.exceptions;

/**
 * Created by bel-sahn on 7/30/19
 */
public class ExampleNotFoundException extends RuntimeException {
//region CONSTRUCTORS
    public ExampleNotFoundException(){
        super();
    }

    public ExampleNotFoundException(String message){
        super(message);
    }

    public ExampleNotFoundException(Throwable cause){
        super(cause);
    }

    public ExampleNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
//endregion

//region GETTERS/SETTERS
//endregion

//region HELPER METHODS
//endregion
}
