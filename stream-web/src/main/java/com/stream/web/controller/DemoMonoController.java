package com.stream.web.controller;

import com.stream.common.model.ReactiveWebRes;
import com.stream.common.protocol.WebMonoProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class DemoMonoController implements WebMonoProtocol {

    @GetMapping(value = "/mono")
    public Mono<ReactiveWebRes<String>> mono() {
        ReactiveWebRes reactiveWebRes = new ReactiveWebRes();
        reactiveWebRes.setData("test");
        return Mono.just(reactiveWebRes);
    }
}
