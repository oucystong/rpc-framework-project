package com.ouc.rpc.framework.proxy;


import net.sf.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * @Description: Cglib动态代理实现
 * @Author: Mr.Tong
 */
@Service
public class CglibProxy implements ProxyService {


    @Override
    public <T> T getProxy(Class<T> interfaceClass) {
        // 使用Cglib动态代理创建代理对象
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(interfaceClass.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(interfaceClass);
        // 设置方法拦截器
        enhancer.setCallback(new CglibMethodInterceptor());
        // 返回代理对象
        return interfaceClass.cast(enhancer.create());
    }
}
