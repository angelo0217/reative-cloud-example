package com.stream.common.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum StreamAction {
    MESSAGE(1, "訊息"),
    ;


    private int code;
    private String desc;
    private static final Map<Integer, StreamAction> MAP_STORE = new HashMap<>();

    private StreamAction(int code, String desc) {
        this.code = code;
        this.desc= desc;
    }

    static {
        Arrays.stream(values()).forEach(streamAction -> MAP_STORE.put(streamAction.getCode(), streamAction));
    }
    public static StreamAction getInstanceOf(int code) {
        return MAP_STORE.get(code);
    }
}
