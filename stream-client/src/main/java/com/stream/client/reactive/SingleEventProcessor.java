package com.stream.client.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Component
@Slf4j
public class SingleEventProcessor extends EventProcessor {
    SingleEventProcessor(EventListener eventListener){
        super(eventListener);
    }
    @Override
    public void executeLogic() {
        this.getEventListener().onDataChunk("" + System.currentTimeMillis());
    }
}
