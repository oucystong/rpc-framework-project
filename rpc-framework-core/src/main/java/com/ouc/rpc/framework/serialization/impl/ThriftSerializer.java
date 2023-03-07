package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;

/**
 * @Description: Thrift序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class ThriftSerializer implements Serializer {


    @Override
    public <T> byte[] serialize(T obj) {
//        TSerializer serializer = null;
//        try {
//            serializer = new TSerializer(new TBinaryProtocol.Factory());
//        } catch (TTransportException e) {
//            throw new RuntimeException(e);
//        }
//        return serializer.serialize(obj);
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz){
//        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
//        T obj = clazz.newInstance();
//        deserializer.deserialize(obj, bytes);
//        return obj;
        return null;
    }
}
