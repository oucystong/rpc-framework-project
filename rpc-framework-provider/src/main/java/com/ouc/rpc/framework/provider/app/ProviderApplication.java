package com.ouc.rpc.framework.provider.app;


import com.ouc.rpc.framework.provider.config.ProviderSpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @Description: 生产者主启动类
 * @Author: Mr.Tong
 */

public class ProviderApplication {
    public static void main(String[] args) throws IOException {
        // 获取项目容器
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ProviderSpringConfig.class);
        // 等待系统输入
        System.in.read();
    }
}
