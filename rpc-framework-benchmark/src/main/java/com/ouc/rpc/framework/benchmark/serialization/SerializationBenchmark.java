package com.ouc.rpc.framework.benchmark.serialization;


import cn.hutool.core.util.IdUtil;
import com.ouc.rpc.framework.benchmark.serialization.model.CommonObject;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.serialization.impl.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 序列化算法性能测试
 * @Author: Mr.Tong
 */
@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@Threads(8)
public class SerializationBenchmark {
    /*************************获得序列化器**************************************************/
    private static final JdkSerializer jdkSerializer = new JdkSerializer();
    private static final JsonSerializer jsonSerializer = new JsonSerializer();
    private static final XmlSerializer xmlSerializer = new XmlSerializer();
    private static final FstSerializer fstSerializer = new FstSerializer();
    private static final HessianSerializer hessianSerializer = new HessianSerializer();
    private static final KryoSerializer kryoSerializer = new KryoSerializer();
    private static final MultipleSerializer multipleSerializer = new MultipleSerializer();
    private static final ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();

    /****************************************构造序列化和反序列化实体****************************/
    private static RpcRequestMessage rpcRequestMessageBean = null;
    private static RpcResponseMessage rpcResponseMessageBean = null;

    private static byte[] jdkDeserializerBean = null;
    private static byte[] jsonDeserializerBean = null;
    private static byte[] xmlDeserializerBean = null;
    private static byte[] fstDeserializerBean = null;
    private static byte[] hessianDeserializerBean = null;
    private static byte[] kryoDeserializerBean = null;
    private static byte[] multipleDeserializerBean = null;
    private static byte[] protoStuffDeserializerBean = null;

    static {
        // 构造请求消息
        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();
        rpcRequestMessage.setSequenceId(IdUtil.simpleUUID());
        rpcRequestMessage.setMethodName("InterfaceMethodName");
        rpcRequestMessage.setInterfaceName("InterfaceName");

        ArrayList<Object> list = new ArrayList<>();
        list.add("string");
        HashMap<String, String> map = new HashMap<>();
        map.put("string", "string");
        CommonObject commonObject = new CommonObject("string", 1, 1L, 1.0F, 1.0D, list, map);

        rpcRequestMessage.setArgs(new CommonObject[]{commonObject, commonObject, commonObject, commonObject, commonObject});
        rpcRequestMessage.setArgTypes(new Class[]{CommonObject.class, CommonObject.class, CommonObject.class, CommonObject.class, CommonObject.class});

        // 构造响应消息
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setSequenceId(IdUtil.simpleUUID());
        rpcResponseMessage.setIsSuccess(true);
        rpcResponseMessage.setResult(commonObject);
        rpcResponseMessage.setError(commonObject);

        // 将请求消息和响应消息赋值给Bean
        rpcRequestMessageBean = rpcRequestMessage;
        rpcResponseMessageBean = rpcResponseMessage;

        // 获得反序列化结果 | 测试反序列化过程
        jdkDeserializerBean = jdkSerializer.serialize(rpcRequestMessageBean);
        jsonDeserializerBean = jsonSerializer.serialize(rpcRequestMessageBean);
        xmlDeserializerBean = xmlSerializer.serialize(rpcRequestMessageBean);
        fstDeserializerBean = fstSerializer.serialize(rpcRequestMessageBean);
        hessianDeserializerBean = hessianSerializer.serialize(rpcRequestMessageBean);
        kryoDeserializerBean = kryoSerializer.serialize(rpcRequestMessageBean);
        multipleDeserializerBean = multipleSerializer.serialize(rpcRequestMessageBean);
        protoStuffDeserializerBean = protoStuffSerializer.serialize(rpcRequestMessageBean);
    }


    /************************测试序列化和反序列化*****************************/
    @Benchmark
    public static byte[] testJdkSerializer() {
        return jdkSerializer.serialize(rpcRequestMessageBean);
    }


    public static void testJdkDeserializer() {
//        jdkSerializer.deserialize()
    }


    @Benchmark
    public static byte[] testJsonSerializer() {
        return jsonSerializer.serialize(rpcRequestMessageBean);
    }


    @Benchmark
    public static byte[] testXmlSerializer() {
        return xmlSerializer.serialize(rpcRequestMessageBean);

    }

    @Benchmark
    public static byte[] testFstSerializer() {
        return fstSerializer.serialize(rpcRequestMessageBean);

    }

    @Benchmark
    public static byte[] testHessianSerializer() {
        return hessianSerializer.serialize(rpcRequestMessageBean);

    }

    @Benchmark
    public static byte[] testKryoSerializer() {
        return kryoSerializer.serialize(rpcRequestMessageBean);

    }

    @Benchmark
    public static byte[] testMultipleSerializer() {
        return multipleSerializer.serialize(rpcRequestMessageBean);

    }


    @Benchmark
    public static byte[] testProtoStuffSerializer() {
        return protoStuffSerializer.serialize(rpcRequestMessageBean);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(SerializationBenchmark.class.getSimpleName()).result("result.json").resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

}
