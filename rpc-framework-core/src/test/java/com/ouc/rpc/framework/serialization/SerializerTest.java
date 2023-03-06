package com.ouc.rpc.framework.serialization;

import com.ouc.rpc.framework.remote.RpcRequest;
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

    private static RpcRequest rpcRequest = new RpcRequest();

    static {
        rpcRequest.setRequestId("id_1");
        rpcRequest.setMethodName("method");
        rpcRequest.setInterfaceName("interface");
        rpcRequest.setTypes(new String[]{"String", "String"});
        rpcRequest.setArgs(new Object[]{"Obj1", "Obj2"});
    }

    @Test
    void testJdkSerializer() {
        JdkSerializer jdkSerializer = new JdkSerializer();
        // 序列化测试
        byte[] serialize = jdkSerializer.serialize(rpcRequest);
        log.info("jdk serialize result: {}", Arrays.toString(serialize));
        log.info("jdk serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequest deserialize = jdkSerializer.deserialize(serialize, RpcRequest.class);
        log.info("jdk deserialize result:{}", deserialize);
    }


    @Test
    void testJsonSerializer() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        // 序列化测试
        byte[] serialize = jsonSerializer.serialize(rpcRequest);
        log.info("json serialize result: {}", Arrays.toString(serialize));
        log.info("json serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequest deserialize = jsonSerializer.deserialize(serialize, RpcRequest.class);
        log.info("json deserialize result:{}", deserialize);
    }

    @Test
    void testXmlSerializer() {
        XmlSerializer xmlSerializer = new XmlSerializer();
        // 序列化测试
        byte[] serialize = xmlSerializer.serialize(rpcRequest);
        log.info("xml serialize result: {}", Arrays.toString(serialize));
        log.info("xml serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequest deserialize = xmlSerializer.deserialize(serialize, RpcRequest.class);
        log.info("xml deserialize result:{}", deserialize);
    }

    @Test
    void testFstSerializer() {
        FstSerializer fstSerializer = new FstSerializer();
        // 序列化测试
        byte[] serialize = fstSerializer.serialize(rpcRequest);
        log.info("fst serialize result: {}", Arrays.toString(serialize));
        log.info("fst serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequest deserialize = fstSerializer.deserialize(serialize, RpcRequest.class);
        log.info("fst deserialize result:{}", deserialize);
    }


    @Test
    void testHessianSerializer() {
        HessianSerializer hessianSerializer = new HessianSerializer();
        // 序列化测试
        byte[] serialize = hessianSerializer.serialize(rpcRequest);
        log.info("hessian serialize result: {}", Arrays.toString(serialize));
        log.info("hessian serialize result length: {}", serialize.length);
        // 反序列化测试
        RpcRequest deserialize = hessianSerializer.deserialize(serialize, RpcRequest.class);
        log.info("hessian deserialize result:{}", deserialize);
    }
}
