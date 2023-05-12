package com.ouc.rpc.framework.model.handler;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Description: service标签解析器
 * @Author: Mr.Tong
 */
public class ServiceBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        // 获取标签各个属性的值
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String impl = element.getAttribute("impl");
        String serialization = element.getAttribute("serialization");
        String netProtocol = element.getAttribute("net-protocol");


        // 创建一个BeanDefinition对象
        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(ExposeServiceModel.class);

        // 设置值
        if (StringUtils.hasText(id)) {
            rootBeanDefinition.getPropertyValues().add("exposeServiceId", id);
        }
        if (StringUtils.hasText(name)) {
            rootBeanDefinition.getPropertyValues().add("exposeServiceName", name);
        }
        if (StringUtils.hasText(impl)) {
            rootBeanDefinition.getPropertyValues().add("exposeServiceImpl", impl);
        }
        if (StringUtils.hasText(serialization)) {
            rootBeanDefinition.getPropertyValues().add("serializerType", serialization);
        }
        if (StringUtils.hasText(netProtocol)) {
            rootBeanDefinition.getPropertyValues().add("networkProtocolType", netProtocol);
        }

        // 将BeanDefinition注册到IOC容器中 | 使用ID作为Bean的Name
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);

        return rootBeanDefinition;
    }
}
