package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.constant.RpcConstant;
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
    /**
     * @Description: 获取接口的代理对象
     */
    public static <T> T getProxy(Class<T> interfaceClass) {
        // 获取用户配置的存根实现方式
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConstant.RPC_CONFIG_PATH);
        String stubProxyKey = properties.getProperty(RpcConstant.RPC_STUB_PROXY);
        ExtensionLoader<ProxyService> proxyServiceExtensionLoader = ExtensionLoader.getExtensionLoader(ProxyService.class);
        ProxyService proxyService = proxyServiceExtensionLoader.getExtension(stubProxyKey);
        return proxyService.getProxy(interfaceClass);
    }


}
