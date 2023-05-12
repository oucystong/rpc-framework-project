package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.Assert;

import java.io.*;
import java.lang.reflect.Type;

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

    private final ObjectOutputStream outputStream;

    private final ByteArrayOutputStream byteArrayOutputStream;

    public JdkSerializer() throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        outputStream = new ObjectOutputStream(byteArrayOutputStream);
    }


    @Override
    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        outputStream.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        outputStream.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        outputStream.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        outputStream.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        outputStream.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        outputStream.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        outputStream.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        outputStream.writeUTF(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            outputStream.writeInt(len);
            outputStream.write(v, off, len);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        outputStream.flush();
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
        return byteArrayOutputStream;
    }

    @Override
    public void setIntputStream(ByteArrayInputStream byteArrayInputStream) {

    }


}
