package com.stream.client.exception;

import lombok.Getter;

@Getter
public class DemoClientException extends RuntimeException {
    public DemoClientException(){

    }

    public DemoClientException(String msg){
        super(msg);
    }

    private ClientCode clientCode;

    private Exception exception;

    private String message;

    public DemoClientException(ClientCode clientCode){
        super(clientCode.toString());
        this.clientCode = clientCode;
    }

    public DemoClientException(ClientCode clientCode, String message) {
        super(clientCode.toString());
        this.clientCode = clientCode;
        this.message = message;
    }

    public DemoClientException(ClientCode clientCode, Exception exception) {
        super(clientCode.toString());
        this.clientCode = clientCode;
        this.exception = exception;
    }

}
