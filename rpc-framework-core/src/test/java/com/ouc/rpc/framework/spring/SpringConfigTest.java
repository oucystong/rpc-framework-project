package com.ouc.rpc.framework.spring;


import com.ouc.rpc.framework.proxy.ProxyFactory;
import com.ouc.rpc.framework.spring.config.SpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class SpringConfigTest {


    @Test
    void testBean() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ProxyFactory bean = annotationConfigApplicationContext.getBean(ProxyFactory.class);
//        bean.testProxy();
    }


    @Test
    void testScope() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ProxyFactory bean1 = annotationConfigApplicationContext.getBean(ProxyFactory.class);
        ProxyFactory bean2 = annotationConfigApplicationContext.getBean(ProxyFactory.class);
        log.info("bean1 == bean2 : {}", bean2 == bean1);
    }
}
