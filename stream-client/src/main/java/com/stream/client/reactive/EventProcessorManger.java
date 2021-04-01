package com.stream.client.reactive;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventProcessorManger {

    @Autowired
    private BeanFactory beanFactory;

    private Map<String, EventListener> eventListenerMap = new ConcurrentHashMap<>();

    private Map<String, List<String>> typeListMap = new ConcurrentHashMap<>();

    public Flux<ServerSentEvent<? extends Serializable>> createEvent(String type, String key){


        Flux process = Flux.create(sink -> {
            EventListener eventListener = beanFactory.getBean(EventListener.class, type, key, sink);
            saveEventListener(type, key, eventListener);
        }, FluxSink.OverflowStrategy.ERROR).map(data -> {
            //如果要用預設  就不用這個map
            ServerSentEvent sse = (ServerSentEvent) data;
            return ServerSentEvent.builder(sse.data()).event("TEST").build();
        });
        
        Flux<ServerSentEvent<String>> ping = Flux.interval(Duration.ofSeconds(2)).map(l -> ServerSentEvent.builder("").event("ping").data("").build());
        return Flux.merge(process, ping);
    }

    public synchronized void saveEventListener(String type, String key, EventListener eventListener){
        this.removeEventProcessor(type, key);
        eventListenerMap.put(this.genKey(type, key), eventListener);

        List<String> typeKeys = typeListMap.get(type);
        if(CollectionUtils.isEmpty(typeKeys)){
            typeKeys = new Vector<>();
        }

        typeKeys.add(key);
        typeListMap.put(type, typeKeys);
    }

    public EventListener getListener(String type, String key){
        return eventListenerMap.get(this.genKey(type, key));
    }

    public void sendAllEventByTypeKey(String type, String key, String message) {
        EventListener eventListener = eventListenerMap.get(this.genKey(type, key));
        if (eventListener != null) {
            if (eventListener.isLinked()) {
                eventListener.onDataChunk(message);
            } else {
                this.removeEventProcessor(type, key);
            }
        }
    }

    public void sendAllEventByType(String type, String message) {
        if (!CollectionUtils.isEmpty(typeListMap.get(type))) {
            List<String> typeKeys = new Vector<>(typeListMap.get(type));
            typeKeys.forEach(key -> {
                this.sendAllEventByTypeKey(type, key, message);
            });
        }
    }

    public synchronized void removeEventProcessor(String type,String key){
        EventListener eventListener = eventListenerMap.get(key);
        if(eventListener != null){
            eventListenerMap.remove(this.genKey(type, key));
        }

        List<String> typeKeys = typeListMap.get(type);
        if(!CollectionUtils.isEmpty(typeKeys)){
            typeKeys.remove(key);
        }
    }

    private String genKey(String type, String key){
        StringBuffer sb = new StringBuffer();
        sb.append(type).append(":").append(key);
        return sb.toString();
    }
}
