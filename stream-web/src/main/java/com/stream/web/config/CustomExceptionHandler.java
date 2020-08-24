package com.stream.web.config;


import com.stream.web.exception.WebFluxException;
import com.stream.web.model.IntegrationRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(basePackages="com.stream.web.controller")
public class CustomExceptionHandler {

    @ExceptionHandler(WebFluxException.class)
    public void handleWebFluxException(WebFluxException webFluxException) {
        log.error("webFluxException === ", webFluxException);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<IntegrationRes> handleCustomException(Exception e) {
        log.error("error === ", e);
        IntegrationRes integrationRes = new IntegrationRes(999, "111 test error", null);
        return Mono.just(integrationRes);
    }

}
