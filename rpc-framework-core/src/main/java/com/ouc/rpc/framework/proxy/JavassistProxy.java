package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: Javassist动态代理实现
 * @Author: Mr.Tong
 */
@Slf4j
public class JavassistProxy implements ProxyService {


    @Override
    public <T> T getProxy(Class<T> interfaceClass, ReferenceServiceModel referenceServiceModel) {
        return null;
    }
}
