package com.ouc.rpc.framework.serialization;

import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.serialization.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class SerializerTest {

    private static RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();

    static {
        rpcRequestMessage.setRequestId("id_1");
        rpcRequestMessage.setMethodName("method");
        rpcRequestMessage.setInterfaceName("interface");
        rpcRequestMessage.setTypes(new Class[]{String.class, String.class});
        rpcRequestMessage.setArgs(new Object[]{"Obj1", "Obj2"});
    }

    @Test
    void testJdkSerializer() {
        JdkSerializer jdkSerializer = new JdkSerializer();
        // 序列化测试
        byte[] serialize = jdkSerializer.serialize(rpcRequestMessage);
        log.info("jdk serialize result: {}", Arrays.toString(serialize));
        log.info("jdk serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = jdkSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("jdk deserialize result:{}", deserialize);
    }


    @Test
    void testJsonSerializer() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        // 序列化测试
        byte[] serialize = jsonSerializer.serialize(rpcRequestMessage);
        log.info("json serialize result: {}", Arrays.toString(serialize));
        log.info("json serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = jsonSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("json deserialize result:{}", deserialize);
    }

    @Test
    void testXmlSerializer() {
        XmlSerializer xmlSerializer = new XmlSerializer();
        // 序列化测试
        byte[] serialize = xmlSerializer.serialize(rpcRequestMessage);
        log.info("xml serialize result: {}", Arrays.toString(serialize));
        log.info("xml serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = xmlSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("xml deserialize result:{}", deserialize);
    }

    @Test
    void testFstSerializer() {
        FstSerializer fstSerializer = new FstSerializer();
        // 序列化测试
        byte[] serialize = fstSerializer.serialize(rpcRequestMessage);
        log.info("fst serialize result: {}", Arrays.toString(serialize));
        log.info("fst serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = fstSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("fst deserialize result:{}", deserialize);
    }


    @Test
    void testHessianSerializer() {
        HessianSerializer hessianSerializer = new HessianSerializer();
        // 序列化测试
        byte[] serialize = hessianSerializer.serialize(rpcRequestMessage);
        log.info("hessian serialize result: {}", Arrays.toString(serialize));
        log.info("hessian serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = hessianSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("hessian deserialize result:{}", deserialize);
    }


    @Test
    void testKryoSerializer() {
        KryoSerializer kryoSerializer = new KryoSerializer();
        // 序列化测试
        byte[] serialize = kryoSerializer.serialize(rpcRequestMessage);
        log.info("kryo serialize result: {}", Arrays.toString(serialize));
        log.info("kryo serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = kryoSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("kryo deserialize result:{}", deserialize);
    }


    @Test
    void testMultipleSerializer() {
        MultipleSerializer multipleSerializer = new MultipleSerializer();
        // 序列化测试
        byte[] serialize = multipleSerializer.serialize(rpcRequestMessage);
        log.info("multiple serialize result: {}", Arrays.toString(serialize));
        log.info("multiple serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = multipleSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("multiple deserialize result:{}", deserialize);
    }


    @Test
    void testProtoStuffSerializer() {
        ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();
        // 序列化测试
        byte[] serialize = protoStuffSerializer.serialize(rpcRequestMessage);
        log.info("protostuff serialize result: {}", Arrays.toString(serialize));
        log.info("protostuff serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequestMessage deserialize = protoStuffSerializer.deserialize(serialize, RpcRequestMessage.class);
        log.info("protostuff deserialize result:{}", deserialize);
    }


}
