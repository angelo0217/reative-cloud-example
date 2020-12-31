package com.stream.client.reactive;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventProcessorManger {

    private Map<String, EventProcessor> eventProcessorMap = new ConcurrentHashMap<>();

    public synchronized void saveEventProcessor(String key, EventProcessor eventProcessor){
        eventProcessorMap.put(key, eventProcessor);
    }

    public synchronized void removeEventProcessor(String key){
        EventProcessor eventProcessor = eventProcessorMap.get(key);
        if(eventProcessor != null){
            eventProcessor.shutdown();
        }
    }
    public EventProcessor getEventProcessor(String key){
        return eventProcessorMap.get(key);
    }
}
