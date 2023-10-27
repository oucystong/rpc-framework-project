package com.ouc.rpc.framework.consumer.app;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import com.ouc.rpc.framework.api.service.NovelAuthorService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class NewTest {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        NovelAuthorService novelAuthorService = classPathXmlApplicationContext.getBean(NovelAuthorService.class);

        // 生成2024B固定长度的字符串
        String fixedLengthStr = FixedLengthStrGenerator.getFixedLengthStr(1024);

        // 10个线程模拟客户端 每个线程执行10000次请求
        ConcurrencyTester tester0 = ThreadUtil.concurrencyTest(10, () -> {
            // 测试的逻辑内容
            for (Integer i = 0; i < 500; i++) {
//                System.out.println(novelAuthorService.getAuthor(fixedLengthStr));
                novelAuthorService.getAuthor(fixedLengthStr);
                System.out.println(Thread.currentThread().getName() + "线程执行第" + i + "次");
            }
        });
        // 获取总的执行时间
        Console.log("10个客户端（5000次请求调用）执行时间: " + tester0.getInterval() / 1000 + "秒" + "-->QPS: " + 5000 / (tester0.getInterval() / 1000));


    }

}
