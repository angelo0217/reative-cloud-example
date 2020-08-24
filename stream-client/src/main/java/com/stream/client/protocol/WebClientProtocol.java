package com.stream.client.protocol;


import com.stream.client.model.IntegrationRes;
import com.stream.client.model.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WebClientProtocol {
    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<UserVo> users();

    @GetMapping(value = "/flux_ex", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> fluxEx();

    @GetMapping(value = "/mono")
    Mono<IntegrationRes<String>> mono();
}
