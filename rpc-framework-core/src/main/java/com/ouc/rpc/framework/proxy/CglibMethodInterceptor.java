package com.ouc.rpc.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: Cglib方法执行拦截增强器
 * @Author: Mr.Tong
 */
@Slf4j
public class CglibMethodInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        // 确定方法名称、方法参数、方法参数类型
        return invoke(method.getName(), args, method.getParameterTypes());
    }

    /**
     * @Description: 方法调用
     */
    private Object invoke(String method, Object[] args, Class[] argTypes) {
        //
        log.info("===+++");
        return null;
    }


}
