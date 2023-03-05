package com.ouc.rpc.framework.app;

import com.ouc.rpc.framework.ProviderService;
import com.ouc.rpc.framework.api.ConsumerUseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.security.Provider;

/**
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ConsumerApplication.class, args);
        ConsumerUseService bean = run.getBean(ConsumerUseService.class);
        bean.testServiceA("xiaotong");
    }
}
