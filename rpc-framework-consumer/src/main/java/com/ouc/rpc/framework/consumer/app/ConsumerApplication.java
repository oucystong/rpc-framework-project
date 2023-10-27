package com.ouc.rpc.framework.consumer.app;


import com.ouc.rpc.framework.api.performance.StrService;
import com.ouc.rpc.framework.api.service.GreetingService;
import com.ouc.rpc.framework.api.service.NovelAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description: 消费者主启动类
 * @Author: Mr.Tong
 */
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        sRPC();
//        performanceTest();
    }


    public static void sRPC() throws IOException {
        // 获取项目容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // 获取Bean-获取的是代理对象
//        GreetingService greetingService = classPathXmlApplicationContext.getBean(GreetingService.class);
        NovelAuthorService novelAuthorService = classPathXmlApplicationContext.getBean(NovelAuthorService.class);
        // 使用Bean中的方法
        String animalFarm = novelAuthorService.getAuthor("Animal Farm");
        SwingUtils.showInfoMessage("Dubbo框架服务返回信息", animalFarm);
//        SwingUtils.showInfoMessage("sRPC框架服务返回信息", animalFarm);
//        System.out.println(xiaotong);
        // 等待系统输入
        System.in.read();
    }

    private GreetingService greetingService;

    public void init() {
        // 获取项目容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // 获取Bean-获取的是代理对象
        greetingService = classPathXmlApplicationContext.getBean(GreetingService.class);
    }

    public void performanceTest(String requestData) throws IOException {

        String str = greetingService.functionTypeServiceBase(requestData);
    }
}
