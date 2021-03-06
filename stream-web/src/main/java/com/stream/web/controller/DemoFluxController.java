package com.stream.web.controller;

import com.stream.common.model.UserVo;
import com.stream.common.protocol.WebFluxProtocol;
import com.stream.web.exception.WebDemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class DemoFluxController implements WebFluxProtocol {

    @GetMapping(value = "/flux")
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

    @Override
    public Flux<UserVo> users() {
        Flux<UserVo> objectFlux = Flux.fromIterable(getUsers()).map(value -> {
            value.setName("name:" + value.getName());
            return value;
        }).delayElements(Duration.ofMillis(1000));
        return objectFlux;
    }

    private List<UserVo> getUsers() {
        List<UserVo> list = new ArrayList<>();
        UserVo userVo = new UserVo();
        userVo.setAge(10);
        userVo.setName("老王");
        list.add(userVo);
        userVo = new UserVo();
        userVo.setAge(15);
        userVo.setName("小三");
        list.add(userVo);
        return list;
    }

    @Override
    public Flux<String> fluxEx() {
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            if (i == 3) {
                throw new WebDemoException("test error");
            }
            return "flux data—" + i;
        })).delayElements(Duration.ofMillis(1000))
//            .onErrorMap(ex -> new RuntimeException("test error2"));
           .doOnError(ex -> log.info("error !!!", ex))
//                .onErrorReturn("flux data—" +Integer.MAX_VALUE)
        ;
        return result;
    }

}
