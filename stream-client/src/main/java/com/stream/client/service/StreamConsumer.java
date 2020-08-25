package com.stream.client.service;


import com.stream.client.config.stream.ReactiveProtocol;
import com.stream.common.constant.StreamAction;
import com.stream.common.model.StreamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class StreamConsumer {

    @StreamListener(ReactiveProtocol.IN_PUT)
    public void receive(@Payload StreamVo streamVo) {
        switch (streamVo.getStreamAction()){
            case MESSAGE:
                log.info("get message {}", streamVo.getMessage());
                break;
        }
    }
}
