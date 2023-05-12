package com.ouc.rpc.framework.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcRequestMessageTest {

    @Test
    void testSetGet() {
        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();
        rpcRequestMessage.setSequenceId(2376L);
        log.info("rpc request id is {}", rpcRequestMessage.getSequenceId());
    }


}
