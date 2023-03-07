package com.ouc.rpc.framework.serialization;

import org.apache.dubbo.common.extension.SPI;

import java.io.IOException;

/**
 * @Description: 序列化和反序列化接口
 * @Author: Mr.Tong
 */
@SPI(value = "jdk")
public interface Serializer {

    /**
     * @Description: 序列化方法
     */
    <T> byte[] serialize(T obj);

    /**
     * @Description: 反序列化方法
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
