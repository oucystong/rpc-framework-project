package com.ouc.rpc.framework.consumer.app;


import com.ouc.rpc.framework.api.ProviderService;
import com.ouc.rpc.framework.consumer.config.ConsumerSpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
//        Dubbo();
        sRPC();
    }


    public static void sRPC() throws IOException {
        // 获取项目容器
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConsumerSpringConfig.class);
        // 获取Bean
        ProviderService providerService = annotationConfigApplicationContext.getBean(ProviderService.class);
        // 使用Bean中的方法
        log.info(providerService.ServiceAMethod("srpc param1"));
        log.info(providerService.ServiceBMethod("srpc param1", "srpc param2"));
        // 等待系统输入
        System.in.read();
    }

    public static void Dubbo() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        context.start();
        ProviderService providerService = context.getBean(ProviderService.class);
        log.info(providerService.ServiceAMethod("dubbo param1"));
        log.info(providerService.ServiceBMethod("dubbo param1", "dubbo param2"));
        System.in.read();

    }
}
