package com.ouc.rpc.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Component;


/**
 * @Description: 生成代理对象的工厂类
 * @Author: Mr.Tong
 */
@Slf4j
@Component
@PropertySource("classpath:proxyconfig.properties")
public class ProxyFactory {

    /**
     * @Description: 根据配置文件中的值确定动态代理的实现方案
     */
    @Value("${proxytype}")
    private String proxyType;

    /**
     * @Description: Cglib动态代理
     */
    @Autowired
    private CglibProxy cglibProxy;

    /**
     * @Description: Jdk动态代理
     */
    @Autowired
    private JdkProxy jdkProxy;

    /**
     * @Description: Javassist动态代理
     */
    @Autowired
    private JavassistProxy javassistProxy;

    /**
     * @Description: 获取接口的代理对象
     */
    public <T> T getProxy(Class<T> interfaceClass) {
        if (proxyType.equals("cglib")) {
            return cglibProxy.getProxy(interfaceClass);
        } else if (proxyType.equals("jdk")) {
            return jdkProxy.getProxy(interfaceClass);
        } else if (proxyType.equals("javassist")) {
            return javassistProxy.getProxy(interfaceClass);
        }
        // 不配置动态代理方式则默认使用Cglib动态代理
        return cglibProxy.getProxy(interfaceClass);
    }


    /**
     * @Description: 测试代码
     */
    public void testProxy() {
        if (proxyType.equals("cglib")) {
            log.info("value is {}", proxyType);
        } else if (proxyType.equals("jdk")) {
            log.info("value is {}", proxyType);
        } else if (proxyType.equals("javassist")) {
            log.info("value is {}", proxyType);
        }
    }


}
