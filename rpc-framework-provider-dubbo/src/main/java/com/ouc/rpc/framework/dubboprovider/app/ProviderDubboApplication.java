package com.ouc.rpc.framework.dubboprovider.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class ProviderDubboApplication {

    public static void main(String[] args) throws IOException {
        Dubbo();
    }


    public static void Dubbo() throws IOException {
        // 获取项目容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // 等待系统输入
        System.in.read();
    }

}
