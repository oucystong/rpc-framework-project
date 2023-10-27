//package com.ouc.rpc.framework.consumer.app;
//
//import com.ouc.rpc.framework.api.service.GreetingService;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.io.IOException;
//
///**
// * @Description:
// * @Author: Mr.Tong
// */
//public class ConsumerDubboApplication {
//
//    public static void main(String[] args) {
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:consumer.xml");
////        Map<String, GreetingsService> beansOfType = classPathXmlApplicationContext.getBeansOfType(GreetingsService.class);
//        GreetingService greetingService = classPathXmlApplicationContext.getBean("greetingService", GreetingService.class);
//        String s = greetingService.functionTypeServiceBase("xiaotong");
//        System.out.println(s);
//
//    }
//
//
//    private GreetingService greetingService;
//    public void init(){
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:consumer.xml");
//        greetingService = classPathXmlApplicationContext.getBean("greetingService", GreetingService.class);
//    }
//
//    public void performanceTest(String requestData) throws IOException {
//        String str = greetingService.functionTypeServiceBase(requestData);
//    }
//
//
//
//}
