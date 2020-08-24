package com.stream.client.controller;

import com.stream.client.model.IntegrationRes;
import com.stream.client.model.UserVo;
import com.stream.client.protocol.DemoWebClient;
import com.stream.client.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoWebClient demoWebClient;

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        long timeMillis = System.currentTimeMillis();
        log.info("webflux() start");
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(I -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data—" + I;
        }));
        log.info("flux() end use time {}/ms", System.currentTimeMillis() - timeMillis);
        return result;
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
    public Flux<UserVo> getWebUser(){
        return demoWebClient.users();
    }

    @GetMapping(value = "/flux_ex", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testEx(){
        return demoWebClient.fluxEx();
    }

    @GetMapping(value = "/mono_ex")
    public Mono<IntegrationRes<String>> testMonoEx(){
        return demoWebClient.monoEx();
    }


    @GetMapping(value = "/client_change", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> clientChange(){
        return demoService.testWebFlux();
    }
}
