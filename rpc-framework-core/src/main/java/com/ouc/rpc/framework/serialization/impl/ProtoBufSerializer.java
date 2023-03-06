package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;

import java.io.IOException;

/**
 * @Description: ProtoBuf序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class ProtoBufSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
//        Schema schema = RuntimeSchema.getSchema(obj.getClass());
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        buffer.clear();
//        return bytes;
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        return null;
    }
}
