package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: Jdk的动态代理对象调用处理器
 * @Author: Mr.Tong
 */
@Slf4j
public class JdkInvocationHandler implements InvocationHandler {

    private ReferenceServiceModel referenceServiceModel;

    public JdkInvocationHandler(ReferenceServiceModel referenceServiceModel) {
        this.referenceServiceModel = referenceServiceModel;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return StubInvokeWrapper.remoteCall(referenceServiceModel, method, args);
    }
}
