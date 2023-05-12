package com.ouc.rpc.framework.model.handler;

import com.ouc.rpc.framework.model.ServerModel;
import com.ouc.rpc.framework.model.ZooKeeperModel;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Description: zk标签解析器
 * @Author: Mr.Tong
 */
public class ZkBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        // 获取标签各个属性的值
        String id = element.getAttribute("id");
        String address = element.getAttribute("address");

        // 创建一个BeanDefinition对象
        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(ZooKeeperModel.class);

        // 设置值
        if (StringUtils.hasText(id)) {
            rootBeanDefinition.getPropertyValues().add("zooKeeperId", id);
        }
        if (StringUtils.hasText(address)) {
            rootBeanDefinition.getPropertyValues().add("zooKeeperAddress", address);
        }

        // 将BeanDefinition注册到IOC容器中
        parserContext.getRegistry().registerBeanDefinition("zooKeeperModel", rootBeanDefinition);

        return rootBeanDefinition;
    }
}
