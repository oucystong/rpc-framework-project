package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import org.apache.dubbo.common.extension.SPI;

/**
 * @Description: 用于生成代理对象的代理服务
 * @Author: Mr.Tong
 */
@SPI("cglib")
public interface ProxyService {
    <T> T getProxy(Class<T> interfaceClass, ReferenceServiceModel referenceServiceModel);
}
