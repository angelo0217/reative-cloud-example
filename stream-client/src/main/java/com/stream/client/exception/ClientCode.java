package com.stream.client.exception;

import lombok.Getter;

@Getter
public enum ClientCode {
    SUCCESS(0, "success"),
    REST_INTEGRATION_ERROR(990, "rest error"),
    SYSTEM_ERROR(999, "system_error");

    private int code;
    private String message;

    ClientCode(){
        //do nothing
    }

    private ClientCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
