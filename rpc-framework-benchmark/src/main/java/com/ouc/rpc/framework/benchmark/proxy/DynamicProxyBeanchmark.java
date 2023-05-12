package com.ouc.rpc.framework.benchmark.proxy;

import com.ouc.rpc.framework.proxy.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description: 三种类型的动态代理性能测试
 * @Author: Mr.Tong
 */
public class DynamicProxyBeanchmark {


    // 运行性能测试方法
    private static ArrayList<Long> runBeanchmark(int type, String classFileDirectoryPath) throws Throwable {
        ArrayList<Long> result = new ArrayList<>();

        // 模拟生成100个客户端需要引用的接口类文件
        for (int i = 0; i < 100; i++) {
            InterfaceGenerator.generate(i + 1);
        }

        // 通过类加载器加载所有的class文件
        ArrayList<Class> aClass = InterfaceLoader.getClass(classFileDirectoryPath);

        // 记录创建的代理对象
        ArrayList<Object> objectArrayList = new ArrayList<>();
        // 记录代理方式
        String typeName = "";

        // 生成代理对象测试
        long startCreateProxyInstance = System.currentTimeMillis();
        switch (type) {
            case 1:
                JdkProxy jdkProxy = new JdkProxy();
                typeName = "Jdk动态代理测试结果";
                for (Class itemClass : aClass) {
                    // 产生接口的代理对象
                    Object proxyInstance = jdkProxy.getProxy(itemClass, null);
                    // 记录代理对象
                    objectArrayList.add(proxyInstance);
                }
                break;
            case 2:
                CglibProxy cglibProxy = new CglibProxy();
                typeName = "Cglib动态代理测试结果";
                for (Class itemClass : aClass) {
                    // 产生接口的代理对象
                    Object proxyInstance = cglibProxy.getProxy(itemClass, null);
                    // 记录代理对象
                    objectArrayList.add(proxyInstance);
                }
                break;
            case 3:
                JavassistProxy javassistProxy = new JavassistProxy();
                typeName = "Javassist动态代理测试结果";
                for (Class itemClass : aClass) {
                    // 产生接口的代理对象
                    Object proxyInstance = javassistProxy.getProxy(itemClass, null);
                    // 记录代理对象
                    objectArrayList.add(proxyInstance);
                }
                break;
        }
        long endCreateProxyInstance = System.currentTimeMillis();


        // 接口代理对象执行方法调用测试
        long startMethodInvoke = System.currentTimeMillis();
        Method request = null;// 避免每次创建浪费时间
        for (Object item : objectArrayList) {
            for (int i = 0; i < 300000; i++) {
                // 获取代理对象的方法
                request = item.getClass().getDeclaredMethod("request", null);
                // 执行调用
                request.invoke(item, null);
            }
        }
        long endMethodInvoke = System.currentTimeMillis();

        System.out.println("*****************" + typeName + "*****************");
        System.out.println("创建接口的代理对象时间消耗: " + (endCreateProxyInstance - startCreateProxyInstance) + "毫秒");
        System.out.println("执行接口方法调用的时间消耗: " + (endMethodInvoke - startMethodInvoke) + "毫秒");

        result.add(endCreateProxyInstance - startCreateProxyInstance);
        result.add(endMethodInvoke - startMethodInvoke);

        return result;
    }

    // 动态代理性能测试
    public static void main(String[] args) throws Throwable {
        // 1-Jdk动态代理测试
        Long[][] jdkResult = new Long[10][10];
        for (int i = 0; i < 10; i++) {
            ArrayList<Long> longs = runBeanchmark(1, "/Users/ethan/Desktop/2023/code/rpc-framework-project/rpc-framework-benchmark/src/main/java/com/ouc/rpc/framework/benchmark/proxy/inter");
            jdkResult[i][0] = longs.get(0);
            jdkResult[i][1] = longs.get(1);
        }
        System.out.println(Arrays.asList(jdkResult));
        // 2-Cglib动态代理测试
        Long[][] cglibResult = new Long[10][10];
        for (int i = 0; i < 10; i++) {
            ArrayList<Long> longs = runBeanchmark(2, "/Users/ethan/Desktop/2023/code/rpc-framework-project/rpc-framework-benchmark/src/main/java/com/ouc/rpc/framework/benchmark/proxy/inter");
            cglibResult[i][0] = longs.get(0);
            cglibResult[i][1] = longs.get(1);
        }
        System.out.println(Arrays.asList(cglibResult));

        // 3-Javassist动态代理测试
        Long[][] javassistResult = new Long[10][10];
        for (int i = 0; i < 10; i++) {
            ArrayList<Long> longs = runBeanchmark(3, "/Users/ethan/Desktop/2023/code/rpc-framework-project/rpc-framework-benchmark/src/main/java/com/ouc/rpc/framework/benchmark/proxy/inter");
            javassistResult[i][0] = longs.get(0);
            javassistResult[i][1] = longs.get(1);
        }
        System.out.println(Arrays.asList(javassistResult));
    }
}
