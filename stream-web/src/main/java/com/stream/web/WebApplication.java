package com.stream.web;

import com.stream.web.config.stream.ReactiveProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(ReactiveProtocol.class)
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
