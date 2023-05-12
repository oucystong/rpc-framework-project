package com.ouc.rpc.framework;

import com.ouc.rpc.framework.benchmark.proxy.InterfaceGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class InterfaceGeneratorTest {


    @Test
    void testGenerateInterface() throws IOException, ClassNotFoundException, URISyntaxException {
        for (int i = 0; i < 100; i++) {
            InterfaceGenerator.generate(i + 1);
        }
    }

    @Test
    void testClassLoader() throws ClassNotFoundException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Class<?> aClass = systemClassLoader.loadClass("com/ouc/rpc/framework/TestService1.class");
    }
}
