package com.stream.client.config;


import com.stream.common.model.ReactiveWebRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(basePackages="com.stream.client.controller")
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleCustomException(Exception e) {
        log.error("error === ", e);
        ReactiveWebRes reactiveWebRes= new ReactiveWebRes(999, "client test error", null);
        return Mono.just(reactiveWebRes);
    }

}
