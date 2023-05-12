//package com.ouc.rpc.framework.model.handler;
//
//import com.ouc.rpc.framework.model.ProxyModel;
//import com.ouc.rpc.framework.model.SerializerModel;
//import com.ouc.rpc.framework.util.RpcCommonUtil;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.beans.factory.xml.BeanDefinitionParser;
//import org.springframework.beans.factory.xml.ParserContext;
//import org.springframework.util.StringUtils;
//import org.w3c.dom.Element;
//
///**
// * @Description:
// * @Author: Mr.Tong
// */
//public class SerializerBeanDefinitionParser implements BeanDefinitionParser {
//
//    @Override
//    public BeanDefinition parse(Element element, ParserContext parserContext) {
//        // 获取标签各个属性的值
//        String id = element.getAttribute("id");
//        String type = element.getAttribute("type");
//
//        // 创建一个BeanDefinition对象
//        RootBeanDefinition rootBeanDefinition = RpcCommonUtil.getBeanDefinition(SerializerModel.class);
//
//        // 设置值
//        if (StringUtils.hasText(id)) {
//            rootBeanDefinition.getPropertyValues().add("serializerId", id);
//        }
//        if (StringUtils.hasText(type)) {
//            rootBeanDefinition.getPropertyValues().add("serializerType", type);
//        }
//
//
//        // 将BeanDefinition注册到IOC容器中
//        parserContext.getRegistry().registerBeanDefinition("serializerModel", rootBeanDefinition);
//
//        return rootBeanDefinition;
//
//    }
//}
