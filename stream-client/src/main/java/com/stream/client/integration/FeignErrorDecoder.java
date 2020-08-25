package com.stream.client.integration;

import com.stream.client.exception.ClientCode;
import com.stream.client.exception.DemoClientException;
import com.stream.common.model.ReactiveWebRes;
import com.stream.common.util.JsonUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.debug("in the decode =======");
        ReactiveWebRes reactiveWebRes = JsonUtil.jsonToObject(response.body().toString(), ReactiveWebRes.class);
        log.error("integration methodKey: {}", methodKey );
        log.error("integration Status code: {}, info: {}", response.status(), response.request().toString());
        if(reactiveWebRes != null){
            return new DemoClientException(ClientCode.REST_INTEGRATION_ERROR, reactiveWebRes.getMessage());
        } else {
            return new DemoClientException(ClientCode.REST_INTEGRATION_ERROR);
        }
    }
}
