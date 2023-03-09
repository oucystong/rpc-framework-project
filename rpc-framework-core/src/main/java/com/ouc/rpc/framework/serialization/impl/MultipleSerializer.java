package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 多种序列化方式融合实现序列化和反序列化
 * @Author: Mr.Tong
 */
@Slf4j
public class MultipleSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        FstSerializer fstSerializer = new FstSerializer();
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] serialize = fstSerializer.serialize(obj);
        byte[] result = kryoSerializer.serialize(serialize);
        log.info("multiple serialize successfully");
        return result;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        FstSerializer fstSerializer = new FstSerializer();
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] deserialize = kryoSerializer.deserialize(bytes, byte[].class);
        T result = fstSerializer.deserialize(deserialize, clazz);
        log.info("multiple deserialize successfully");
        return result;
    }
}
