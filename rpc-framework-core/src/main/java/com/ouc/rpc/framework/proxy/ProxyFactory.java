package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.model.ProxyModel;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;


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
        // 判断用户是否配置 | 若配置则使用用户配置的相关内容 | 否则使用默认的动态代理方式
        if (ApplicationContextUtil.containsBean("proxyModel")) {// 存在用户配置
            ProxyModel proxyModel = ApplicationContextUtil.getApplicationContext().getBean("proxyModel", ProxyModel.class);
            PROXY_SERVICE = extensionLoader.getExtension(proxyModel.getProxyType());
            log.info("use {} dynamic proxy", proxyModel.getProxyType());
        } else {// 使用默认的动态代理方式
            PROXY_SERVICE = extensionLoader.getDefaultExtension();
            log.info("use default dynamic proxy");
        }
    }

}
