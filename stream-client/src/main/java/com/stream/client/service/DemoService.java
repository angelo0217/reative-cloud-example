package com.stream.client.service;

import com.stream.client.model.UserVo;
import com.stream.client.protocol.DemoWebClient;
import com.stream.client.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DemoService {

    @Autowired
    private DemoWebClient demoWebClient;

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

    public Flux<String> testWebFlux(){
//        List<String> aa = new ArrayList<>();
        Flux<UserVo> flux = demoWebClient.users();
//
//        flux.subscribe(userVo -> {
//            System.out.println("~~~~~~");
//            aa.add(JsonUtil.objectToJson(userVo));
//        });
//
//        return Flux.fromIterable(aa).map(value -> {
//            return value + "aa";
//        }).delayElements(Duration.ofMillis(1000));
        return flux.map(userVo -> {
           return JsonUtil.objectToJson(userVo);
        });
    }
}
