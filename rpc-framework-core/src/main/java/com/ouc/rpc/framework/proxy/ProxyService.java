package com.ouc.rpc.framework.proxy;

import org.apache.dubbo.common.extension.SPI;

import java.lang.reflect.Proxy;

/**
 * @Description: 用于生成代理对象的代理服务
 * @Author: Mr.Tong
 */
@SPI("cglib")
public interface ProxyService {
    public <T> T getProxy(Class<T> interfaceClass);
}
