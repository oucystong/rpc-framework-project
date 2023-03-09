package com.ouc.rpc.framework.consumer.app;


import com.ouc.rpc.framework.ProviderService;
import com.ouc.rpc.framework.proxy.ProxyFactory;
import com.ouc.rpc.framework.spring.RpcSpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * z
 *
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) {
        // 获得RPC框架容器
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(RpcSpringConfig.class);
        // 获得RPC服务接口的代理对象-即获得本地存根
        ProxyFactory proxyFactory = annotationConfigApplicationContext.getBean(ProxyFactory.class);
        ProviderService providerService = proxyFactory.getProxy(ProviderService.class);
        providerService.ServiceAMethod("ServiceAMethodTest");
        proxyFactory.testProxy();
    }
}
