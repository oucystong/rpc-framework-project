package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

/**
 * @Description: Fst序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class FstSerializer implements Serializer {
    private static FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    @Override
    public <T> byte[] serialize(T obj) {
        return conf.asByteArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return clazz.cast(conf.asObject(bytes));
    }
}
