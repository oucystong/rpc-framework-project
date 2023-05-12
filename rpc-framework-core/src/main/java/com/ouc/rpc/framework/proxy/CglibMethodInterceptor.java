package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: Cglib方法执行拦截增强器
 * @Author: Mr.Tong
 */
@Slf4j
public class CglibMethodInterceptor implements MethodInterceptor {


    private ReferenceServiceModel referenceServiceModel;

    public CglibMethodInterceptor(ReferenceServiceModel referenceServiceModel) {
        this.referenceServiceModel = referenceServiceModel;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws InvocationTargetException, IllegalAccessException {
        return StubInvokeWrapper.remoteCall(referenceServiceModel, method, args);
    }


}
