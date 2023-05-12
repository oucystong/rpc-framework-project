package com.ouc.rpc.framework.model.handler;

import com.ouc.rpc.framework.model.ApplicationModel;
import com.ouc.rpc.framework.model.ClientModel;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Description: client标签解析器
 * @Author: Mr.Tong
 */
public class ClientBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        // 获取标签各个属性的值
        String id = element.getAttribute("id");
        String loadBalance = element.getAttribute("load-balance");

        // 创建一个BeanDefinition对象
        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(ClientModel.class);

        // 设置值
        if (StringUtils.hasText(id)) {
            rootBeanDefinition.getPropertyValues().add("clientId", id);
        }
        if (StringUtils.hasText(loadBalance)) {
            rootBeanDefinition.getPropertyValues().add("clientLoadBalance", loadBalance);
        }

        // 将BeanDefinition注册到IOC容器中
        parserContext.getRegistry().registerBeanDefinition("clientModel", rootBeanDefinition);

        return rootBeanDefinition;
    }
}
