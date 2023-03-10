package com.ouc.rpc.framework.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Description: Bean的后置处理器
 * @Author: Mr.Tong
 */
@Slf4j
public class BeanProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * @Description: 初始化之后
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("后置处理器：初始化之后执行 Bean {} : BeanName {}", bean, beanName);
        return bean;
    }
}
