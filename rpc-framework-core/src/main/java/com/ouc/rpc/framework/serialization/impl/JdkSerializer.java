package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @Description: Jdk序列化和反序列化实现
 * @Author: Mr.Tong
 */
@Slf4j
public class JdkSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            log.info("jdk serialize successfully");
        } catch (IOException e) {
            log.error("jdk serialize has exception: {}", e.getMessage());
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                log.error("close stream has exception:{}", e.getMessage());
            }
        }
        return baos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(bais);
            o = ois.readObject();
            log.info("jdk deserialize successfully");
        } catch (Exception e) {
            log.error("jdk deserialize has exception:{}", e.getMessage());
        } finally {
            try {
                ois.close();
                bais.close();
            } catch (IOException e) {
                log.error("close stream has exception: {}", e.getMessage());
            }
        }
        return clazz.cast(o);
    }
}
