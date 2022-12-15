package com.limyel.bridge.common.serializer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.limyel.bridge.common.constant.SerializeAlgorithmConstant;
import com.limyel.bridge.common.serializer.Serializer;

import java.io.IOException;

public class JSONSerializer implements Serializer {

    public static final JSONSerializer INSTANCE = new JSONSerializer();

    private final ObjectMapper objectMapper;

    private JSONSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte getSerializeAlgorithm() {
        return SerializeAlgorithmConstant.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // todo 统一异常处理
            throw new RuntimeException();
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
