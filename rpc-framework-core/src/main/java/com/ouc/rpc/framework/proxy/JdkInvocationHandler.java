package com.ouc.rpc.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: Jdk的动态代理对象调用处理器
 * @Author: Mr.Tong
 */
@Slf4j
public class JdkInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 接口方法执行

        // 将请求参数进行序列化

        // 网络传输

        // 等待结果

        // 获得结果
        log.info("|||||||||||");

        // 返回结果
        return null;
    }
}
