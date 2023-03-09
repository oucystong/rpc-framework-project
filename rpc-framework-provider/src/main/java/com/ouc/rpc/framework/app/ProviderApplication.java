package com.ouc.rpc.framework.app;


import com.ouc.rpc.framework.registry.zk.ZkServiceRegistry;
import com.ouc.rpc.framework.registry.zk.ZooKeeperClient;
import com.ouc.rpc.framework.spring.RpcSpringConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @Description: 生产者主启动类
 * @Author: Mr.Tong
 */

public class ProviderApplication {
    public static void main(String[] args) throws IOException {
        // 获得RPC框架容器
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(RpcSpringConfig.class);
//        System.in.read();
        ZkServiceRegistry zkServiceRegistry = new ZkServiceRegistry();
        zkServiceRegistry.serviceRegistry();
        System.in.read();
    }
}
