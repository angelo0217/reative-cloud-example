package com.stream.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private String name;
    private int age;
}
