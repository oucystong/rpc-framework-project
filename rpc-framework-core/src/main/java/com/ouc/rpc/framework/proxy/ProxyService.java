package com.ouc.rpc.framework.proxy;

import java.lang.reflect.Proxy;

/**
 * @Description: 用于生成代理对象的代理服务
 * @Author: Mr.Tong
 */
public interface ProxyService {
    public <T> T getProxy(Class<T> interfaceClass);
}
