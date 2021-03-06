package com.stream.client.controller;

import com.stream.client.reactive.EventProcessorManger;
import com.stream.client.service.DemoService;
import com.stream.common.model.ReactiveWebRes;
import com.stream.common.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;

@Slf4j
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private EventProcessorManger eventProcessorManger;


    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        return demoService.flux();
    }

    @GetMapping(value = "/flux2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux2() {
        return demoService.testFlux();
    }

    @GetMapping(value = "/flux3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux3() {
        return demoService.testFlux2();
    }

    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserVo> getWebUser(@RequestParam("id") String id) {
        log.info(">>>>>>>>>>>" + id);
        return demoService.getWebUser();
    }

    @GetMapping(value = "/flux_ex", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testEx() {
        return demoService.testEx().map((val) -> {
            System.out.println("~~~~~~~~~" + val);
            return val;
        });
    }

    @GetMapping(value = "/mono")
    public Mono<ReactiveWebRes<String>> testMono() {
        return demoService.getWebMono();
    }

    @GetMapping(value = "/mono_ex")
    public Mono<ReactiveWebRes<String>> testMonoEx() {
        return demoService.getWebMonoEx();
    }


    @GetMapping(value = "/client_change", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> clientChange() {
        return demoService.testWebFlux();
    }

    private static String TYPE = "type1";

    private static String KEY = "test";

    @GetMapping("/close")
    public void close() {
        eventProcessorManger.getListener(TYPE, KEY).processComplete();
    }

    @GetMapping("/add")
    public void addData() {
        eventProcessorManger.sendAllEventByTypeKey(TYPE, KEY, "aaaa");
    }

    @GetMapping("/sendTpe")
    public void sendTpe() {
        eventProcessorManger.sendAllEventByType(TYPE, "hello");
    }

    @GetMapping("/get_queue")
    public Flux<ServerSentEvent<? extends Serializable>> getQueue() {
        return eventProcessorManger.createEvent(TYPE, KEY);
    }
}
