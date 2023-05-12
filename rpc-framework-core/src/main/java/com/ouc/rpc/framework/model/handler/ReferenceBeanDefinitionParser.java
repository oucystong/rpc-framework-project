package com.ouc.rpc.framework.model.handler;

import com.ouc.rpc.framework.model.ProxyModel;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: reference标签解析器
 * @Author: Mr.Tong
 */
public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        // 获取标签各个属性的值
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String rpcFramework = element.getAttribute("rpc-framework");


        // 创建一个BeanDefinition对象
        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(ReferenceServiceModel.class);

        // 设置值
        if (StringUtils.hasText(id)) {
            rootBeanDefinition.getPropertyValues().add("referenceServiceId", id);
        }
        if (StringUtils.hasText(name)) {
            rootBeanDefinition.getPropertyValues().add("referenceServiceName", name);
        }
        if (StringUtils.hasText(rpcFramework)) {
            rootBeanDefinition.getPropertyValues().add("providerRpcFramework", rpcFramework);
        }

        // 将BeanDefinition注册到IOC容器中
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);

        return rootBeanDefinition;
    }
}
