package com.stream.web.controller;

import com.stream.common.constant.StreamAction;
import com.stream.common.model.StreamVo;
import com.stream.web.service.StreamMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamDemoController {

    @Autowired
    private StreamMessageProducer streamMessageProducer;

    @GetMapping("/send")
    public void send(){
        StreamVo streamVo = new StreamVo();
        streamVo.setStreamAction(StreamAction.MESSAGE);
        streamVo.setMessage("test 12345");
        streamMessageProducer.send(streamVo);
    }
}
