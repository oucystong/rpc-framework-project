package com.ouc.rpc.framework.serialization;

import org.apache.dubbo.common.extension.SPI;

import java.io.IOException;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@SPI("jdk")
public interface ObjectOutput extends DataOutput{
    /**
     * write object.
     *
     * @param obj object.
     */
    void writeObject(Object obj) throws IOException;

}
