package com.limyel.bridge.serialize;

import com.limyel.bridge.serialize.impl.JSONSerializer;

/**
 * @author limyel
 * @since 2023-02-07 22:14
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
