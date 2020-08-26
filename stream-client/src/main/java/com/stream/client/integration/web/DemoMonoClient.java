package com.stream.client.integration.web;

import com.stream.client.integration.ReactiveWeBasicConfiguration;
import com.stream.common.protocol.WebMonoProtocol;
import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient( name = "stream-web", path = "web", configuration = ReactiveWeBasicConfiguration.class)
public interface DemoMonoClient extends WebMonoProtocol {
}
