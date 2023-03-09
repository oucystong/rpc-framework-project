package com.ouc.rpc.framework.config;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

/**
 * @Description: 客户端在服务发现时需要引用的信息模型
 * @Author: Mr.Tong
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReferenceConfig implements InitializingBean, ApplicationContextAware, FactoryBean, Serializable {

    /**
     * @Description: 应用上下文
     */
    private transient ApplicationContext applicationContext;


    /**
     * @Description: 服务实例IP
     */
    private String directServerIp;

    /**
     * @Description: 服务实例端口
     */
    private int directServerPort;


    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
