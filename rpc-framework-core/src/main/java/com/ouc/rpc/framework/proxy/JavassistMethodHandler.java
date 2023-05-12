package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;

/**
 * @Description: Javassist的动态代理对象调用处理器
 * @Author: Mr.Tong
 */
public class JavassistMethodHandler implements MethodHandler {

    private ReferenceServiceModel referenceServiceModel;

    public JavassistMethodHandler(ReferenceServiceModel referenceServiceModel) {
        this.referenceServiceModel = referenceServiceModel;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        return StubInvokeWrapper.remoteCall(referenceServiceModel, thisMethod, args);
    }

}
