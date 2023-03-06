package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description: Avro序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class AvroSerializer implements Serializer {

    private static final EncoderFactory ENCODER_FACTORY = EncoderFactory.get();
    private static final DecoderFactory DECODER_FACTORY = DecoderFactory.get();

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
//        Schema schema = new ReflectDatumWriter<>(obj.getClass()).getSchema();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        BinaryEncoder encoder = ENCODER_FACTORY.binaryEncoder(baos, null);
//        DatumWriter<T> writer = new ReflectDatumWriter<>(schema);
//        writer.write(obj, encoder);
//        encoder.flush();
//        baos.close();
//        return baos.toByteArray();
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
//        Schema schema = new ReflectDatumReader<>(clazz).getSchema();
//        BinaryDecoder decoder = DECODER_FACTORY.binaryDecoder(bytes, null);
//        DatumReader<T> reader = new ReflectDatumReader<>(schema);
//        return clazz.cast(reader.read(null, decoder));
        return null;
    }
}
