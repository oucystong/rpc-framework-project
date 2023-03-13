package com.ouc.rpc.framework.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcResponseMessageTest {

    @Test
    void testSetGet() {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setIsSuccess(false);
        log.info("rpc response is {}", rpcResponseMessage.getIsSuccess().toString());
    }
}
