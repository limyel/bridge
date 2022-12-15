package com.limyel.bridge.common.serializer;

/**
 * @author limyel
 */
public interface Serializer {

    /**
     * 获取序列化算法
     * @return
     */
    byte getSerializeAlgorithm();

    /**
     * 序列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
