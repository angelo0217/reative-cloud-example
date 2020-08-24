package com.stream.client.protocol;

import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient( name = "stream-web", path = "web", configuration = MbWeBasicConfiguration.class)
public interface DemoWebClient extends WebClientProtocol {
}
