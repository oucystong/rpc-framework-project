package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: ProtoStuff序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class ProtoStuffSerializer implements Serializer {

    private final Map<Class<?>, RuntimeSchema<?>> schemaCache = new ConcurrentHashMap<>();

    private <T> RuntimeSchema<T> getSchema(Class<T> clazz) {
        return (RuntimeSchema<T>) schemaCache.computeIfAbsent(clazz, RuntimeSchema::createFrom);
    }


    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            RuntimeSchema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        try {
            T message = clazz.newInstance();
            RuntimeSchema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException(e);
        }
    }
}
