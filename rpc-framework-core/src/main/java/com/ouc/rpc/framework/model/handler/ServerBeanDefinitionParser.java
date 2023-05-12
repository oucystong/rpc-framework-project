package com.ouc.rpc.framework.model.handler;

import com.ouc.rpc.framework.model.ClientModel;
import com.ouc.rpc.framework.model.ServerModel;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Description: server标签解析器
 * @Author: Mr.Tong
 */
public class ServerBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        // 获取标签各个属性的值
        String id = element.getAttribute("id");
        String port = element.getAttribute("port");


        // 创建一个BeanDefinition对象
        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(ServerModel.class);

        // 设置值
        if (StringUtils.hasText(id)) {
            rootBeanDefinition.getPropertyValues().add("serverId", id);
        }
        if (StringUtils.hasText(port)) {
            rootBeanDefinition.getPropertyValues().add("serverPort", port);
        }

        // 将BeanDefinition注册到IOC容器中
        parserContext.getRegistry().registerBeanDefinition("serverModel", rootBeanDefinition);

        return rootBeanDefinition;
    }
}
