package com.ouc.rpc.framework.serialization.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.ouc.rpc.framework.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class Hessian2Serializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(baos);
        try {
            ho.writeObject(obj);
            log.info("hessian2 serialize successfully");
        } catch (IOException e) {
            log.error("hessian2 serialize has exception: {}", e.getMessage());
        } finally {
            try {
                ho.close();
                baos.close();
            } catch (IOException e) {
                log.error("close stream has exception: {}", e.getMessage());
            }
        }
        return baos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input hi = new Hessian2Input(bais);
        Object o = null;
        try {
            o = hi.readObject();
            log.info("hessian deserialize successfully");
        } catch (IOException e) {
            log.error("hessian deserialize has exception: {}", e.getMessage());
        } finally {
            try {
                bais.close();
                hi.close();
            } catch (IOException e) {
                log.error("close stream has exception: {}", e.getMessage());
            }
        }
        return clazz.cast(o);
    }


    private final Hessian2Output mH2o;

    private final ByteArrayOutputStream byteArrayOutputStream;

    private Hessian2Input mH2i;


    public Hessian2Serializer() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        mH2o = new Hessian2Output(byteArrayOutputStream);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        mH2o.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        mH2o.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        mH2o.writeDouble(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        mH2o.writeDouble(v);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        mH2o.writeBytes(b);
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        mH2o.writeBytes(b, off, len);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        mH2o.writeString(v);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        mH2o.writeObject(obj);
    }

    @Override
    public void flushBuffer() throws IOException {
        mH2o.flushBuffer();
    }


    @Override
    public boolean readBool() throws IOException {
        return mH2i.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) mH2i.readInt();
    }

    @Override
    public short readShort() throws IOException {
        return (short) mH2i.readInt();
    }

    @Override
    public int readInt() throws IOException {
        return mH2i.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return mH2i.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return (float) mH2i.readDouble();
    }

    @Override
    public double readDouble() throws IOException {
        return mH2i.readDouble();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return mH2i.readBytes();
    }

    @Override
    public String readUTF() throws IOException {
        return mH2i.readString();
    }

    @Override
    public Object readObject() throws IOException {
        return mH2i.readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException,
            ClassNotFoundException {
        return (T) mH2i.readObject(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return readObject(cls);
    }


    @Override
    public ByteArrayOutputStream getOutputStream() {
        return byteArrayOutputStream;
    }

    @Override
    public void setIntputStream(ByteArrayInputStream byteArrayInputStream) {
        mH2i=new Hessian2Input(byteArrayInputStream);
    }


}
