package com.stream.web.config.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReactiveProtocol {
    String OUT_PUT = "reactive_demo_output";

    @Output(OUT_PUT)
    MessageChannel publisher();
}
