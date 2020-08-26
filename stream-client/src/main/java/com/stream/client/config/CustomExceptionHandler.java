package com.stream.client.config;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.stream.client.exception.ClientCode;
import com.stream.client.exception.DemoClientException;
import com.stream.common.model.ReactiveWebRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(basePackages="com.stream.client")
public class CustomExceptionHandler {

    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleHystrixRuntimeException(HystrixRuntimeException e) {
        if(e.getCause() instanceof DemoClientException){
            return this.handleDemoClientException((DemoClientException) e.getCause());
        }
        log.error("HystrixRuntimeException error ", e);
        ReactiveWebRes reactiveWebRes= new ReactiveWebRes(ClientCode.HYSTRIX_ERROR.getCode(), ClientCode.HYSTRIX_ERROR.getMessage(), null);
        return Mono.just(reactiveWebRes);
    }

    @ExceptionHandler(DemoClientException.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleDemoClientException(DemoClientException e) {
        log.error("demo error === ", e);
        ReactiveWebRes reactiveWebRes= new ReactiveWebRes(e.getClientCode().getCode(), e.getClientCode().getMessage(), null);
        return Mono.just(reactiveWebRes);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ReactiveWebRes> handleCustomException(Exception e) {
        log.error("error === ", e);
        ReactiveWebRes reactiveWebRes= new ReactiveWebRes(ClientCode.SYSTEM_ERROR.getCode(), ClientCode.SYSTEM_ERROR.getMessage(), null);
        return Mono.just(reactiveWebRes);
    }

}
