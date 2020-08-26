package com.stream.client.integration;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveWeBasicConfiguration {
    @Bean
    public Request.Options options() {
        return new Request.Options(
                30000,
                30000
        );
    }

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
