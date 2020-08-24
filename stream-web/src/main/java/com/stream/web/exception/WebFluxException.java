package com.stream.web.exception;

public class WebFluxException extends RuntimeException {
    public WebFluxException(){

    }

    public WebFluxException(String msg){
        super(msg);
    }
}
