package com.stream.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationRes<T> {
    private int code = 0;
    private String message = "success";
    private T data;
}
