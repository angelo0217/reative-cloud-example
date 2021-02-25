package com.stream.client.reactive;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventProcessorManger {

    private Map<String, EventProcessor> eventProcessorMap = new ConcurrentHashMap<>();

    private Map<String, List<String>> typeListMap = new ConcurrentHashMap<>();

    public synchronized void saveEventProcessor(String type, String key, EventProcessor eventProcessor){
        this.removeEventProcessor(type, key);
        eventProcessorMap.put(this.genKey(type, key), eventProcessor);
        List<String> typeKeys = typeListMap.get(type);
        if(CollectionUtils.isEmpty(typeKeys)){
            typeKeys = new Vector<>();
        }
        typeKeys.add(key);
        typeListMap.put(type, typeKeys);
    }

    public void sendAllEventByType(String type, String message){
        List<String> typeKeys = typeListMap.get(type);
        if(!CollectionUtils.isEmpty(typeKeys)){
            typeKeys.forEach(key ->{
                EventProcessor eventProcessor = eventProcessorMap.get(this.genKey(type, key));
                if(eventProcessor != null){
                    eventProcessor.executeLogic(message);
                }
            });
        }
    }

    public synchronized void removeEventProcessor(String type,String key){
        EventProcessor eventProcessor = eventProcessorMap.get(key);
        if(eventProcessor != null){
            eventProcessor.shutdown();
        }

        List<String> typeKeys = typeListMap.get(type);
        if(!CollectionUtils.isEmpty(typeKeys)){
            typeKeys.remove(key);
        }
    }
    public EventProcessor getEventProcessor(String type, String key){
        return eventProcessorMap.get(this.genKey(type, key));
    }

    private String genKey(String type, String key){
        StringBuffer sb = new StringBuffer();
        sb.append(type).append(":").append(key);
        return sb.toString();
    }
}
