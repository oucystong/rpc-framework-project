package com.ouc.rpc.framework.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcResponseTest {


    @Test
    void testSetGet() {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setIsSuccess(false);
        log.info("rpc response is {}", rpcResponse.getIsSuccess().toString());
    }


}
