package com.stream.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactiveWebRes<T> {
    private int code;
    private String message;
    private T data;
}
