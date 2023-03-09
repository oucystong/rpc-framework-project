package com.ouc.rpc.framework.spi.model;

import org.apache.dubbo.common.extension.SPI;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@SPI
public interface Sport {
    public void sayHello();
    public void sayBye();
}
