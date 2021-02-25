package com.stream.client.reactive;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

@Scope("prototype")
@Component
@Slf4j
@Setter
public class EventListener<T> {
    EventListener(){
        //do noting
    }
    EventListener(String type, String key, FluxSink<T> sink){
        this.type = type;
        this.key = key;
        this.sink = sink;
    }
    private String type;
    private String key;
    private FluxSink<T> sink;
    private FluxSink<T> mainSink;

    @Autowired
    private EventProcessorManger eventProcessorManger;

    public void onDataChunk(T chunk) {
        log.info("data: {}, cancel: {}", chunk, sink.isCancelled());
        if(sink.isCancelled()){
            try{
                sink.complete();
                eventProcessorManger.removeEventProcessor(type, key);
            } catch (Exception ex){
                log.error("shutdown error", ex);
            }
        } else {
            //ServerSentEvent.builder(chunk).build()
            sink.next(chunk);
        }
    }

    public boolean isLinked(){
        if(sink != null) {
            return !sink.isCancelled();
        }
        return false;
    }

    public void processComplete() {
        log.info("complete");
        sink.complete();
//        mainSink.complete();
    }
}

