package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Javassist动态代理实现
 * @Author: Mr.Tong
 */
@Slf4j
public class JavassistProxy implements ProxyService {


    @Override
    public <T> T getProxy(Class<T> interfaceClass, ReferenceServiceModel referenceServiceModel) {

        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setInterfaces(new Class[]{interfaceClass});

        Class<ProxyObject> proxyClass = (Class<ProxyObject>) proxyFactory.createClass();
        ProxyObject proxyObject = null;

        try {
            proxyObject = proxyClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        proxyObject.setHandler(new JavassistMethodHandler(referenceServiceModel));

        log.info("javassist proxy has been created");
        return interfaceClass.cast(proxyObject);
    }
}
