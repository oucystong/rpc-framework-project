package com.ouc.rpc.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;

/**
 * @Description: Jdk动态代理实现
 * @Author: Mr.Tong
 */
@Slf4j
@Service
public class JdkProxy implements ProxyService {


    @Override
    public <T> T getProxy(Class<T> interfaceClass) {
        // 使用Jdk动态代理创建代理对象
        Object o = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new JdkInvocationHandler());
        // 返回代理对象
        return interfaceClass.cast(o);
    }
}
