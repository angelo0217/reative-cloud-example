package com.stream.common.protocol;


import com.stream.common.model.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

public interface WebFluxProtocol {
    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<UserVo> users();

    @GetMapping(value = "/flux_ex", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> fluxEx();
}
