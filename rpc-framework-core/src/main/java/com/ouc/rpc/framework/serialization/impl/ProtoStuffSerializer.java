package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: ProtoStuff序列化和反序列化实现
 * @Author: Mr.Tong
 */
@Slf4j
public class ProtoStuffSerializer implements Serializer {

    private final Map<Class<?>, RuntimeSchema<?>> schemaCache = new ConcurrentHashMap<>();

    private <T> RuntimeSchema<T> getSchema(Class<T> clazz) {
        return (RuntimeSchema<T>) schemaCache.computeIfAbsent(clazz, RuntimeSchema::createFrom);
    }


    @Override
    public <T> byte[] serialize(T obj) {
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        RuntimeSchema<T> schema = getSchema(clazz);
        byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        buffer.clear();
        log.info("protostuff serialize successfully");
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T message = null;
        try {
            message = clazz.newInstance();
            RuntimeSchema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            log.info("protostuff deserialize successfully");
        } catch (Exception e) {
            log.error("protostuff deserialize has exception: {}", e.getMessage());
        }
        return message;
    }
}
