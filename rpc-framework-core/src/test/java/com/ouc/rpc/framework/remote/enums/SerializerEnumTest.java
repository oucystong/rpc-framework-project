package com.ouc.rpc.framework.remote.enums;

import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class SerializerEnumTest {

    @Test
    void testGetID() {
        Integer json = SerializerEnum.idOfType("json");
        System.out.println(json);


    }
}
