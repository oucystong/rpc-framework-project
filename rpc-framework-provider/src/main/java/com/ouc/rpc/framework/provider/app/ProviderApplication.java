package com.ouc.rpc.framework.provider.app;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description: 生产者主启动类
 * @Author: Mr.Tong
 */

public class ProviderApplication {
    public static void main(String[] args) throws IOException {
        sRPC();
    }


    public static void sRPC() throws IOException {
        // 获取项目容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // 等待系统输入
        System.in.read();
    }

}
