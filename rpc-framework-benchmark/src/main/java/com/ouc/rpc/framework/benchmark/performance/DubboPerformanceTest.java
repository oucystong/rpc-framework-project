package com.ouc.rpc.framework.benchmark.performance;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class DubboPerformanceTest {
    public static void main(String[] args) {
//        // 生成2024B固定长度的字符串
//        String fixedLengthStr = FixedLengthStrGenerator.getFixedLengthStr(1024);
//
//        ConsumerDubboApplication consumerDubboApplication = new ConsumerDubboApplication();
//        consumerDubboApplication.init();
//
//        // 10个线程模拟客户端 每个线程执行10000次请求
//        ConcurrencyTester tester0 = ThreadUtil.concurrencyTest(1, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                try {
//                    consumerDubboApplication.performanceTest(fixedLengthStr);
//                    System.out.println(Thread.currentThread().getName() + "线程执行第" + i + "次");
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        });
//        // 获取总的执行时间
//        Console.log("1个客户端（100000次请求调用）执行时间: " + tester0.getInterval() / 1000 + "秒" + "-->QPS: " + 100000 / (tester0.getInterval() / 1000));
//
//
//
//        // 10个线程模拟客户端 每个线程执行10000次请求
//        ConcurrencyTester tester1 = ThreadUtil.concurrencyTest(10, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                try {
//                    consumerDubboApplication.performanceTest(fixedLengthStr);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        });
//        // 获取总的执行时间
//        Console.log("10个客户端（100000次请求调用）执行时间: " + tester1.getInterval() / 1000 + "秒" + "-->QPS: " + 1000000 / (tester1.getInterval() / 1000));
//
//
//        // 20个线程模拟客户端 每个线程执行200000次请求
//        ConcurrencyTester tester2 = ThreadUtil.concurrencyTest(20, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                try {
//                    consumerDubboApplication.performanceTest(fixedLengthStr);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        });
//        // 获取总的执行时间
//        Console.log("20个客户端（100000次请求调用）执行时间: " + tester2.getInterval() / 1000 + "秒" + "-->QPS: " + 2000000 / (tester2.getInterval() / 1000));
//
//
//        // 50个线程模拟客户端 每个线程执行50000次请求
//        ConcurrencyTester tester3 = ThreadUtil.concurrencyTest(50, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                try {
//                    consumerDubboApplication.performanceTest(fixedLengthStr);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        });
//        // 获取总的执行时间
//        Console.log("50个客户端（100000次请求调用）执行时间: " + tester3.getInterval() / 1000 + "秒" + "-->QPS: " + 5000000 / (tester3.getInterval() / 1000));
//
//
//        // 10个线程模拟客户端 每个线程执行10000次请求
//        ConcurrencyTester tester4 = ThreadUtil.concurrencyTest(100, () -> {
//            // 测试的逻辑内容
//            for (Integer i = 0; i < 100000; i++) {
//                try {
//                    consumerDubboApplication.performanceTest(fixedLengthStr);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        });
//        // 获取总的执行时间
//        Console.log("100个客户端（100000次请求调用）执行时间: " + tester4.getInterval() / 1000 + "秒" + "-->QPS: " + 10000000 / (tester4.getInterval() / 1000));
//



    }

}
