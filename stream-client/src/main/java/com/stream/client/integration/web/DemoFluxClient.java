package com.stream.client.integration.web;

import com.stream.client.integration.MbWeBasicConfiguration;
import com.stream.common.protocol.WebFluxProtocol;
import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient( name = "stream-web", path = "web", configuration = MbWeBasicConfiguration.class)
public interface DemoFluxClient extends WebFluxProtocol {
}
