package com.stream.web.config;


import com.stream.common.model.ReactiveWebRes;
import com.stream.web.exception.WebDemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(basePackages="com.stream.web.controller")
public class CustomExceptionHandler {

    @ExceptionHandler(WebDemoException.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleWebFluxException(WebDemoException webDemoException) {
        ReactiveWebRes reactiveWebRes = new ReactiveWebRes(111, webDemoException.getMessage(), null);
        log.error("webFluxException === ", webDemoException);
        return Mono.just(reactiveWebRes);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleCustomException(Exception e) {
        log.error("error === ", e);
        ReactiveWebRes reactiveWebRes = new ReactiveWebRes(999, "111 test error", null);
        return Mono.just(reactiveWebRes);
    }

}
