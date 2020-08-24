package com.stream.client.protocol;

import com.stream.client.exception.ClientCode;
import com.stream.client.exception.DemoClientException;
import com.stream.client.model.IntegrationRes;
import com.stream.client.util.JsonUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.debug("in the decode =======");
        IntegrationRes integrationRes = JsonUtil.jsonToObject(response.body().toString(), IntegrationRes.class);
        log.error("integration methodKey: {}", methodKey );
        log.error("integration Status code: {}, info: {}", response.status(), response.request().toString());
        if(integrationRes != null){
            return new DemoClientException(ClientCode.REST_INTEGRATION_ERROR, integrationRes.getMessage());
        } else {
            return new DemoClientException(ClientCode.REST_INTEGRATION_ERROR);
        }
    }
}
