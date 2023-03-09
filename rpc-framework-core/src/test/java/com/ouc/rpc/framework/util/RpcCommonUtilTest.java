package com.ouc.rpc.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcCommonUtilTest {


    @Test
    void testisCorrectIpPort() {
        log.info(String.valueOf(RpcCommonUtil.isCorrectIpPort("127.45.92.234:8080")));
    }
}
