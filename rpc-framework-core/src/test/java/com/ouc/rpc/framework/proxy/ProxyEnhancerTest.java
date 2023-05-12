package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.registry.ServiceRegistry;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class ProxyEnhancerTest {


    @Test
    void testJavassistProxy() {
        JavassistProxy javassistProxy = new JavassistProxy();
        ServiceRegistry proxy = javassistProxy.getProxy(ServiceRegistry.class, null);
    }

}
