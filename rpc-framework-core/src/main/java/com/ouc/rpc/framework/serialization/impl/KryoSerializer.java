package com.ouc.rpc.framework.serialization.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description: Kryo序列化和反序列化实现
 * @Author: Mr.Tong
 */
@Slf4j
public class KryoSerializer implements Serializer {
    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream baos = null;
        Output output = null;
        try {
            baos = new ByteArrayOutputStream();
            output = new Output(baos);
            KRYO_THREAD_LOCAL.get().writeObject(output, obj);
            output.flush();
            log.info("kryo serialize successfully");
        } catch (KryoException e) {
            log.error("kryo serialize has exception: {}", e.getMessage());
        } finally {
            try {
                baos.close();
                output.close();
            } catch (IOException e) {
                log.error("close stream has exception: {}", e.getMessage());
            }
        }
        return baos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T t = null;
        ByteArrayInputStream bais = null;
        Input input = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            input = new Input(bais);
            t = KRYO_THREAD_LOCAL.get().readObject(input, clazz);
            log.info("kryo deserialize successfully");
        } catch (Exception e) {
            log.error("kryo deserialize has exception: {}", e.getMessage());
        } finally {
            try {
                bais.close();
                input.close();
            } catch (IOException e) {
                log.error("close stream has exception: {}", e.getMessage());
            }
        }
        return t;
    }
}
