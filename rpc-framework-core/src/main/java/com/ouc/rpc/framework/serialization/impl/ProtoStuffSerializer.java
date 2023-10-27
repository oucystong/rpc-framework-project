package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
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

    @Override
    public void writeObject(Object obj) throws IOException {

    }

    @Override
    public void writeBool(boolean v) throws IOException {

    }

    @Override
    public void writeByte(byte v) throws IOException {

    }

    @Override
    public void writeShort(short v) throws IOException {

    }

    @Override
    public void writeInt(int v) throws IOException {

    }

    @Override
    public void writeLong(long v) throws IOException {

    }

    @Override
    public void writeFloat(float v) throws IOException {

    }

    @Override
    public void writeDouble(double v) throws IOException {

    }

    @Override
    public void writeUTF(String v) throws IOException {

    }

    @Override
    public void writeBytes(byte[] v) throws IOException {

    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {

    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public boolean readBool() throws IOException {
        return false;
    }

    @Override
    public byte readByte() throws IOException {
        return 0;
    }

    @Override
    public short readShort() throws IOException {
        return 0;
    }

    @Override
    public int readInt() throws IOException {
        return 0;
    }

    @Override
    public long readLong() throws IOException {
        return 0;
    }

    @Override
    public float readFloat() throws IOException {
        return 0;
    }

    @Override
    public double readDouble() throws IOException {
        return 0;
    }

    @Override
    public String readUTF() throws IOException {
        return null;
    }

    @Override
    public byte[] readBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        return null;
    }

    @Override
    public void setIntputStream(ByteArrayInputStream byteArrayInputStream) {

    }
}
