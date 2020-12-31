package com.stream.client.reactive;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

@Scope("prototype")
@Component
@Slf4j
@Setter
public class EventListener<T> {
    private FluxSink sink;
    private EventProcessor eventProcessor;

    public void onDataChunk(Object chunk) {
        log.info("data: {}, cancel: {}", chunk, sink.isCancelled());
        if(sink.isCancelled()){
            sink.complete();
            eventProcessor.shutdown();
        } else {
            sink.next(chunk);
        }
    }
    public void processComplete() {
        log.info("complete");
        sink.complete();
        eventProcessor.shutdown();
    }
}

