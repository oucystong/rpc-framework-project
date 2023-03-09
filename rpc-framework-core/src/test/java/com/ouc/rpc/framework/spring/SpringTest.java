package com.ouc.rpc.framework.spring;

import com.ouc.rpc.framework.spring.model.Student;
import com.ouc.rpc.framework.spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class SpringTest {


    /**
     * @Description: 测试Scope属性
     */
    @Test
    void testScope() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        Student bean1 = ac.getBean(Student.class);
        Student bean2 = ac.getBean(Student.class);
        log.info("bean1 == bean2 : {}", bean2 == bean1);
    }


    /**
     * @Description: 测试Bean的生命周期
     */
    @Test
    public void testLife() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        User user = ac.getBean(User.class);
        log.info("对象准备就绪可以使用");
        ac.close();
    }


}
