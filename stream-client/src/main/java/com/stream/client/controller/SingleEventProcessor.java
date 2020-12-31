package com.stream.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class SingleEventProcessor {

    private EventListener<String> eventListener;

    public void register(EventListener<String> eventListener) {
        this.eventListener = eventListener;
    }

    public void processComplete() {
        eventListener.onDataChunk(System.currentTimeMillis()+"");
    }

    public void shutdown() {
        eventListener = null;
        log.debug("shutdown");
    }

}
