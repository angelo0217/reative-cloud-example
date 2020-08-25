package com.stream.common.model;

import com.stream.common.constant.StreamAction;
import lombok.Data;

@Data
public class StreamVo {
    private StreamAction streamAction;
    private String message;
}
