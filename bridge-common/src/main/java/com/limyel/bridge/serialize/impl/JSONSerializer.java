package com.limyel.bridge.serialize.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.limyel.bridge.serialize.Serializer;
import com.limyel.bridge.serialize.SerializerAlgorithm;

import java.io.IOException;

/**
 * @author limyel
 * @since 2023-02-07 22:26
 */
public class JSONSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JSONSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // todo 异常处理
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            // todo 异常处理
            throw new RuntimeException(e);
        }
    }
}
