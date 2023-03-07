package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

/**
 * @Description: Fst序列化和反序列化实现
 * @Author: Mr.Tong
 */
@Slf4j
public class FstSerializer implements Serializer {
    private static FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    @Override
    public <T> byte[] serialize(T obj) {
        byte[] bytes = conf.asByteArray(obj);
        log.info("fst serialize successfully");
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T t = clazz.cast(conf.asObject(bytes));
        log.info("fst deserialize successfully");
        return t;
    }
}
