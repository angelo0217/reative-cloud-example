package com.stream.client.reactive;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class EventProcessor<T> {
    private EventListener eventListener;

    public EventProcessor(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public abstract void executeLogic(String message);

    public void shutdown() {
        eventListener = null;
        log.debug("shutdown");
    }
}

