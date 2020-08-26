package com.stream.client.integration.web;

import com.stream.client.integration.ReactiveWeBasicConfiguration;
import com.stream.common.protocol.WebFluxProtocol;
import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient( name = "stream-web", path = "web", configuration = ReactiveWeBasicConfiguration.class)
public interface DemoFluxClient extends WebFluxProtocol {
}
