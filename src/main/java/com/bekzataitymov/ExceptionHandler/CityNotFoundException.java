package com.bekzataitymov.ExceptionHandler;
public class CityNotFoundException extends RuntimeException{
    private String message;
    public CityNotFoundException(String message){
        super(message);

    }


}
