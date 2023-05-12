package com.ouc.rpc.framework.serialization;


import org.apache.dubbo.common.extension.SPI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

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

    /*------------基本类型序列化方法---------------*/

    void writeObject(Object obj) throws IOException;

    void writeBool(boolean v) throws IOException;


    void writeByte(byte v) throws IOException;


    void writeShort(short v) throws IOException;


    void writeInt(int v) throws IOException;


    void writeLong(long v) throws IOException;


    void writeFloat(float v) throws IOException;


    void writeDouble(double v) throws IOException;


    void writeUTF(String v) throws IOException;


    void writeBytes(byte[] v) throws IOException;


    void writeBytes(byte[] v, int off, int len) throws IOException;


    void flushBuffer() throws IOException;



    boolean readBool() throws IOException;


    byte readByte() throws IOException;


    short readShort() throws IOException;


    int readInt() throws IOException;


    long readLong() throws IOException;


    float readFloat() throws IOException;


    double readDouble() throws IOException;


    String readUTF() throws IOException;

    byte[] readBytes() throws IOException;

    Object readObject() throws IOException, ClassNotFoundException;


    <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;


    <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException;






    ByteArrayOutputStream getOutputStream();

    void setIntputStream(ByteArrayInputStream byteArrayInputStream);

}
