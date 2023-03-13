package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.PropertiesFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Component;

import java.util.Properties;


/**
 * @Description: 生成代理对象的工厂类
 * @Author: Mr.Tong
 */
@Slf4j
public class ProxyFactory {

    // 在类加载的时候确定动态代理方式
    public static ProxyService PROXY_SERVICE = null;

    // 在静态变量之后执行
    static {
        ExtensionLoader<ProxyService> extensionLoader = ExtensionLoader.getExtensionLoader(ProxyService.class);
        PROXY_SERVICE = extensionLoader.getDefaultExtension();
    }


//    /**
//     * @Description: 获取接口的代理对象
//     */
//    public static <T> T getProxy(Class<T> interfaceClass, ReferenceServiceModel referenceServiceModel) {
//        // 获取用户配置的存根实现方式
//        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConstant.RPC_CONFIG_PATH);
//        String stubProxyKey = properties.getProperty(RpcConstant.RPC_STUB_PROXY);
//
//        return proxyService.getProxy(interfaceClass, referenceServiceModel);
//    }


}
