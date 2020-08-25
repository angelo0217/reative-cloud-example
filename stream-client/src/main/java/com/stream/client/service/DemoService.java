package com.stream.client.service;

import com.stream.client.integration.web.DemoFluxClient;
import com.stream.client.integration.web.DemoMonoClient;
import com.stream.common.model.ReactiveWebRes;
import com.stream.common.model.UserVo;
import com.stream.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Service
public class DemoService {

    @Autowired
    private DemoFluxClient demoFluxClient;

    @Autowired
    private DemoMonoClient demoMonoClient;

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

    public Flux<String> testFlux(){
        List<String> strs = new ArrayList<>();
        strs.add("2");
        strs.add("21");
        strs.add("22");
        strs.add("23");

        return Flux.fromIterable(strs).delayElements(Duration.ofMillis(1000));
    }

    public Flux<String> testFlux2(){
        List<String> strs = new ArrayList<>();
        strs.add("2");
        strs.add("21");
        strs.add("22");
        strs.add("23");

        return Flux.fromIterable(strs).map(value -> {
            return value + "aa";
        }).delayElements(Duration.ofMillis(1000));
    }

    public Flux<UserVo> getWebUser(){
        return demoFluxClient.users();
    }

    public Flux<String> testEx() {
        return demoFluxClient.fluxEx();
    }

    public Flux<String> testWebFlux(){
        Flux<UserVo> flux = demoFluxClient.users();
        return flux.map(userVo -> {
           return JsonUtil.objectToJson(userVo);
        });
    }

    public Mono<ReactiveWebRes<String>> getWebMono(){
        return demoMonoClient.mono();
    }
}
