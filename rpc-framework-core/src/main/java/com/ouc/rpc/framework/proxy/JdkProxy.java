package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;

/**
 * @Description: Jdk动态代理实现
 * @Author: Mr.Tong
 */
@Slf4j
public class JdkProxy implements ProxyService {


    @Override
    public <T> T getProxy(Class<T> interfaceClass, ReferenceServiceModel referenceServiceModel) {
        // 使用Jdk动态代理创建代理对象
        Object o = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new JdkInvocationHandler(referenceServiceModel));
        log.info("jdk proxy has been created");
        // 返回代理对象
        return interfaceClass.cast(o);
    }
}
