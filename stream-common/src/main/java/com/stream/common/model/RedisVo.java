package com.stream.common.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RedisVo implements Serializable {
    private String name;
    private BigDecimal age;
}
