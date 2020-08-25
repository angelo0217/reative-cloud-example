package com.stream.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            gen.writeNumber(value.setScale(0, BigDecimal.ROUND_HALF_DOWN));
        } else {
            gen.writeNumber(BigDecimal.ZERO);
        }
    }
}