package com.ouc.rpc.framework.benchmark.performance;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import com.ouc.rpc.framework.api.performance.StrService;
import com.ouc.rpc.framework.consumer.app.ConsumerApplication;

import java.io.IOException;
import java.rmi.Naming;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class sRPCPerformanceTest {


    public static void main(String[] args) {
        // 生成2024B固定长度的字符串
        String fixedLengthStr = FixedLengthStrGenerator.getFixedLengthStr(1024);

        ConsumerApplication consumerApplication = new ConsumerApplication();
        consumerApplication.init();

        // 10个线程模拟客户端 每个线程执行10000次请求
        ConcurrencyTester tester0 = ThreadUtil.concurrencyTest(1, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("1个客户端（100000次请求调用）执行时间: " + tester0.getInterval() / 1000 + "秒" + "-->QPS: " + 100000 / (tester0.getInterval() / 1000));


        ConcurrencyTester tester00 = ThreadUtil.concurrencyTest(5, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("5个客户端（100000次请求调用）执行时间: " + tester00.getInterval() / 1000 + "秒" + "-->QPS: " + 500000 / (tester00.getInterval() / 1000));





        // 10个线程模拟客户端 每个线程执行10000次请求
        ConcurrencyTester tester1 = ThreadUtil.concurrencyTest(10, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("10个客户端（100000次请求调用）执行时间: " + tester1.getInterval() / 1000 + "秒" + "-->QPS: " + 1000000 / (tester1.getInterval() / 1000));


        ConcurrencyTester tester10 = ThreadUtil.concurrencyTest(15, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("15个客户端（100000次请求调用）执行时间: " + tester10.getInterval() / 1000 + "秒" + "-->QPS: " + 1500000 / (tester10.getInterval() / 1000));





        // 20个线程模拟客户端 每个线程执行200000次请求
        ConcurrencyTester tester2 = ThreadUtil.concurrencyTest(20, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("20个客户端（100000次请求调用）执行时间: " + tester2.getInterval() / 1000 + "秒" + "-->QPS: " + 2000000 / (tester2.getInterval() / 1000));



        ConcurrencyTester tester20 = ThreadUtil.concurrencyTest(25, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("25个客户端（100000次请求调用）执行时间: " + tester20.getInterval() / 1000 + "秒" + "-->QPS: " + 2500000 / (tester20.getInterval() / 1000));


        ConcurrencyTester tester21 = ThreadUtil.concurrencyTest(30, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("30个客户端（100000次请求调用）执行时间: " + tester21.getInterval() / 1000 + "秒" + "-->QPS: " + 3000000 / (tester21.getInterval() / 1000));

        ConcurrencyTester tester22 = ThreadUtil.concurrencyTest(35, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("35个客户端（100000次请求调用）执行时间: " + tester22.getInterval() / 1000 + "秒" + "-->QPS: " + 3500000 / (tester22.getInterval() / 1000));


        ConcurrencyTester tester23 = ThreadUtil.concurrencyTest(40, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("40个客户端（100000次请求调用）执行时间: " + tester23.getInterval() / 1000 + "秒" + "-->QPS: " + 4000000 / (tester23.getInterval() / 1000));


        ConcurrencyTester tester24 = ThreadUtil.concurrencyTest(45, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("45个客户端（100000次请求调用）执行时间: " + tester24.getInterval() / 1000 + "秒" + "-->QPS: " + 4500000 / (tester24.getInterval() / 1000));




        // 50个线程模拟客户端 每个线程执行50000次请求
        ConcurrencyTester tester3 = ThreadUtil.concurrencyTest(50, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("50个客户端（100000次请求调用）执行时间: " + tester3.getInterval() / 1000 + "秒" + "-->QPS: " + 5000000 / (tester3.getInterval() / 1000));


        ConcurrencyTester tester30 = ThreadUtil.concurrencyTest(55, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("55个客户端（100000次请求调用）执行时间: " + tester30.getInterval() / 1000 + "秒" + "-->QPS: " + 5500000 / (tester30.getInterval() / 1000));


        ConcurrencyTester tester31 = ThreadUtil.concurrencyTest(60, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("60个客户端（100000次请求调用）执行时间: " + tester31.getInterval() / 1000 + "秒" + "-->QPS: " + 6000000 / (tester31.getInterval() / 1000));



        ConcurrencyTester tester32 = ThreadUtil.concurrencyTest(65, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("65个客户端（100000次请求调用）执行时间: " + tester32.getInterval() / 1000 + "秒" + "-->QPS: " + 6500000 / (tester32.getInterval() / 1000));


        ConcurrencyTester tester33 = ThreadUtil.concurrencyTest(70, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("70个客户端（100000次请求调用）执行时间: " + tester33.getInterval() / 1000 + "秒" + "-->QPS: " + 7000000 / (tester33.getInterval() / 1000));



        ConcurrencyTester tester34 = ThreadUtil.concurrencyTest(75, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("75个客户端（100000次请求调用）执行时间: " + tester34.getInterval() / 1000 + "秒" + "-->QPS: " + 7500000 / (tester34.getInterval() / 1000));



        ConcurrencyTester tester35 = ThreadUtil.concurrencyTest(80, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("80个客户端（100000次请求调用）执行时间: " + tester35.getInterval() / 1000 + "秒" + "-->QPS: " + 8000000 / (tester35.getInterval() / 1000));



        ConcurrencyTester tester36 = ThreadUtil.concurrencyTest(85, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("85个客户端（100000次请求调用）执行时间: " + tester36.getInterval() / 1000 + "秒" + "-->QPS: " + 8500000 / (tester36.getInterval() / 1000));



        ConcurrencyTester tester37 = ThreadUtil.concurrencyTest(90, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("90个客户端（100000次请求调用）执行时间: " + tester37.getInterval() / 1000 + "秒" + "-->QPS: " + 9000000 / (tester37.getInterval() / 1000));



        ConcurrencyTester tester38 = ThreadUtil.concurrencyTest(95, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("95个客户端（100000次请求调用）执行时间: " + tester38.getInterval() / 1000 + "秒" + "-->QPS: " + 9500000 / (tester38.getInterval() / 1000));







        // 10个线程模拟客户端 每个线程执行10000次请求
        ConcurrencyTester tester4 = ThreadUtil.concurrencyTest(100, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 100000; i++) {
                try {
                    consumerApplication.performanceTest(fixedLengthStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        // 获取总的执行时间
        Console.log("100个客户端（100000次请求调用）执行时间: " + tester4.getInterval() / 1000 + "秒" + "-->QPS: " + 10000000 / (tester4.getInterval() / 1000));





    }






}
