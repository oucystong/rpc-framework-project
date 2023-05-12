package com.ouc.rpc.framework.consumer.app;


import com.ouc.rpc.framework.api.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        sRPC();
    }


    public static void sRPC() throws IOException {
        // 获取项目容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // 获取Bean-获取的是代理对象
        GreetingService greetingService = classPathXmlApplicationContext.getBean(GreetingService.class);
        // 使用Bean中的方法
        String xiaotong = greetingService.functionTypeServiceBase("xiaotong");
        System.out.println(xiaotong);
        // 等待系统输入
        System.in.read();
    }
}
