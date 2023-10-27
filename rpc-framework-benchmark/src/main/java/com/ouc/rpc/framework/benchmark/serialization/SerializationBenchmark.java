package com.ouc.rpc.framework.benchmark.serialization;


import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;

import com.ouc.rpc.framework.benchmark.model.CommonObject;
import com.ouc.rpc.framework.benchmark.performance.FixedLengthStrGenerator;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.serialization.impl.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 序列化算法性能测试
 * @Author: Mr.Tong
 */

public class SerializationBenchmark {
    /*************************获得序列化器**************************************************/
    private static final JdkSerializer jdkSerializer;
    private static final JsonSerializer jsonSerializer;
    private static final XmlSerializer xmlSerializer;
    private static final FstSerializer fstSerializer;
    private static final HessianSerializer hessianSerializer;
    private static final Hessian2Serializer hessian2Serializer;
    private static final KryoSerializer kryoSerializer;
    private static final ProtoStuffSerializer protoStuffSerializer;

    static {
        jdkSerializer = new JdkSerializer();
        jsonSerializer = new JsonSerializer();
        xmlSerializer = new XmlSerializer();
        fstSerializer = new FstSerializer();
        hessian2Serializer = new Hessian2Serializer();
        hessianSerializer = new HessianSerializer();
        kryoSerializer = new KryoSerializer();
        protoStuffSerializer = new ProtoStuffSerializer();

    }

    /****************************************构造序列化和反序列化实体****************************/

    public static void main(String[] args) {
        String fixedLengthStr = FixedLengthStrGenerator.getFixedLengthStr(1024);

        ConcurrencyTester tester0 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = jdkSerializer.serialize(fixedLengthStr);
                String deserialize = jdkSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("JDK: " + tester0.getInterval() / 1000 + "秒");


        ConcurrencyTester tester1 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = jsonSerializer.serialize(fixedLengthStr);
                String deserialize = jsonSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("Json: " + tester1.getInterval() / 1000 + "秒");


//        ConcurrencyTester tester2 = ThreadUtil.concurrencyTest(20, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                byte[] serialize = xmlSerializer.serialize(fixedLengthStr);
//                String deserialize = xmlSerializer.deserialize(serialize, String.class);
//            }
//        });
//        // 获取总的执行时间
//        Console.log("Xml: " + tester2.getInterval() / 1000 + "秒");



        ConcurrencyTester tester3 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = fstSerializer.serialize(fixedLengthStr);
                String deserialize = fstSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("Fst: " + tester3.getInterval() / 1000 + "秒");


        ConcurrencyTester tester4 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = hessian2Serializer.serialize(fixedLengthStr);
                String deserialize = hessian2Serializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("Hessian2: " + tester4.getInterval() / 1000 + "秒");


        ConcurrencyTester tester5 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = hessianSerializer.serialize(fixedLengthStr);
                String deserialize = hessianSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("Hessian: " + tester5.getInterval() / 1000 + "秒");


        ConcurrencyTester tester6 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = kryoSerializer.serialize(fixedLengthStr);
                String deserialize = kryoSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("Kryo: " + tester6.getInterval() / 1000 + "秒");


        ConcurrencyTester tester7 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                byte[] serialize = protoStuffSerializer.serialize(fixedLengthStr);
                String deserialize = protoStuffSerializer.deserialize(serialize, String.class);
            }
        });
        // 获取总的执行时间
        Console.log("ProtoStuff: " + tester7.getInterval() / 1000 + "秒");
    }


}
