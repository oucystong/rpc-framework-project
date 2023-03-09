package com.ouc.rpc.framework.spi;

import com.ouc.rpc.framework.spi.model.Sport;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class SpiTest {


    @Test
    void testSpi() {
        ExtensionLoader<Sport> extensionLoader = ExtensionLoader.getExtensionLoader(Sport.class);
        Sport sport = extensionLoader.getExtension("run");
        sport.sayHello();
        sport.sayBye();

    }
}
