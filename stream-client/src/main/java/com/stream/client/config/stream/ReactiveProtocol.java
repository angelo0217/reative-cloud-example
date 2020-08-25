package com.stream.client.config.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ReactiveProtocol {
    String IN_PUT = "reactive_message_input";

    @Input(IN_PUT)
    SubscribableChannel subscriber();
}
