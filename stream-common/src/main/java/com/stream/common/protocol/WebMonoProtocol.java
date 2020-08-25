package com.stream.common.protocol;


import com.stream.common.model.ReactiveWebRes;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

public interface WebMonoProtocol {

    @GetMapping(value = "/mono")
    Mono<ReactiveWebRes<String>> mono();
}
