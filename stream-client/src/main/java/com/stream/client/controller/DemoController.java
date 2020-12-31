package com.stream.client.controller;

import com.stream.client.service.DemoService;
import com.stream.common.model.ReactiveWebRes;
import com.stream.common.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;
    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        return demoService.flux();
    }

    @GetMapping(value = "/flux2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux2() {
        return demoService.testFlux();
    }

    @GetMapping(value = "/flux3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux3() {
        return demoService.testFlux2();
    }

    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserVo> getWebUser(@RequestParam("id") String id){
        log.info(">>>>>>>>>>>" + id);
        return demoService.getWebUser();
    }

    @GetMapping(value = "/flux_ex", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testEx(){
        return demoService.testEx().map((val) ->{
            System.out.println("~~~~~~~~~" + val);
            return val;
        });
    }

    @GetMapping(value = "/mono")
    public Mono<ReactiveWebRes<String>> testMono(){
        return demoService.getWebMono();
    }

    @GetMapping(value = "/mono_ex")
    public Mono<ReactiveWebRes<String>> testMonoEx(){
        return demoService.getWebMonoEx();
    }


    @GetMapping(value = "/client_change", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> clientChange(){
        return demoService.testWebFlux();
    }


    private SingleEventProcessor singleEventProcessor = new SingleEventProcessor();

    @GetMapping("/add")
    public void addData(){
        singleEventProcessor.processComplete();
    }

    @GetMapping("/get_queue")
    public Flux<String> getQueue(){
        return Flux.create(sink->{
            singleEventProcessor.register(new EventListener<String>() {
                @Override
                public void onDataChunk(String chunk) {
                    log.info("data: {}, cancel: {}", chunk, sink.isCancelled());
                    if(sink.isCancelled()){
                        sink.complete();
                        singleEventProcessor.shutdown();
                    } else {
                        sink.next(chunk);
                    }
                }
                @Override
                public void processComplete() {
                    log.info("complete");
                    sink.complete();
                    singleEventProcessor.shutdown();
                }
            });
        });
    }
}
