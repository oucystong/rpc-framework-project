package com.ouc.rpc.framework.serialization.impl;

import com.alibaba.fastjson.JSON;
import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;


/**
 * @Description: Json序列化和反序列化方法实现
 * @Author: Mr.Tong
 */
@Slf4j
public class JsonSerializer implements Serializer {


    @Override
    public <T> byte[] serialize(T obj) {
        String json = JSON.toJSONString(obj);
        byte[] bytes = json.getBytes();
        log.info("json serialize successfully");
        return bytes;
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = null;
//        try {
//            jsonString = objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return jsonString.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        String json = new String(bytes);
        T t = JSON.parseObject(json, clazz);
        log.info("json deserialize successfully");
        return t;
//        ObjectMapper objectMapper = new ObjectMapper();
//        T t = null;
//        try {
//            t = objectMapper.readValue(new String(bytes), clazz);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return t;
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
