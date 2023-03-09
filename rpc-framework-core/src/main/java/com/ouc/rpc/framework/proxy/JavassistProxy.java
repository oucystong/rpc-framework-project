package com.ouc.rpc.framework.proxy;

import org.springframework.stereotype.Service;

/**
 * @Description: Javassist动态代理实现
 * @Author: Mr.Tong
 */
@Service
public class JavassistProxy implements ProxyService{


    @Override
    public <T> T getProxy(Class<T> interfaceClass) {
        return null;
    }
}
