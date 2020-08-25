package com.stream.web.service;


import com.stream.common.model.StreamVo;
import com.stream.common.util.JsonUtil;
import com.stream.web.config.stream.ReactiveProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;


@Slf4j
@Service
public class StreamMessageProducer {
    @Autowired(required = true)
    @Output(ReactiveProtocol.OUT_PUT)
    private MessageChannel channel;

    public void send(StreamVo streamVo){
        try {
            channel.send(MessageBuilder.withPayload(streamVo)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
        } catch (Exception ex){
            log.error("send stream error {}", JsonUtil.objectToJson(streamVo), ex);
        }
    }

}
