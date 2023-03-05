package com.ouc.rpc.framework.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcRequestTest {

    @Test
    void testSetGet() {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId("1");
        log.info("rpc request id is {}", rpcRequest.getRequestId());
    }


}
