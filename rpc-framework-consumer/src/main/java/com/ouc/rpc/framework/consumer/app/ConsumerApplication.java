package com.ouc.rpc.framework.consumer.app;


import com.ouc.rpc.framework.api.ProviderService;
import com.ouc.rpc.framework.consumer.api.ConsumerService;
import com.ouc.rpc.framework.consumer.config.ConsumerSpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        // 获取项目容器
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConsumerSpringConfig.class);
        // 获取Bean
//        ConsumerService consumerService = annotationConfigApplicationContext.getBean(ConsumerService.class);
        // 使用Bean中的方法
//        log.info(consumerService.testAMethod("param1"));
//        log.info(consumerService.testBMethod("param1", "param2"));
        // 等待系统输入
        ProviderService pro = annotationConfigApplicationContext.getBean(ProviderService.class);
        System.out.println(pro.ServiceAMethod("================================"));
        System.in.read();
    }
}
