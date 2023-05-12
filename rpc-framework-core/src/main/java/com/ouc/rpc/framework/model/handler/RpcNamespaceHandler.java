package com.ouc.rpc.framework.model.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Description: 命名空间处理器
 * @Author: Mr.Tong
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        // 注册标签解析器-所有的标签都需要在此处进行注册
        registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser());
        registerBeanDefinitionParser("server", new ServerBeanDefinitionParser());
        registerBeanDefinitionParser("client", new ClientBeanDefinitionParser());
        registerBeanDefinitionParser("zk", new ZkBeanDefinitionParser());
        registerBeanDefinitionParser("proxy", new ProxyBeanDefinitionParser());
//        registerBeanDefinitionParser("serializer", new SerializerBeanDefinitionParser());
        registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser());
        registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser());
    }
}
