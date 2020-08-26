package com.stream.web.exception;

public class WebDemoException extends RuntimeException {
    public WebDemoException(){

    }

    public WebDemoException(String msg){
        super(msg);
    }
}
