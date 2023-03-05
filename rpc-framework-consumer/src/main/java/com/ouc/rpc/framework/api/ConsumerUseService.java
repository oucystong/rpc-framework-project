package com.ouc.rpc.framework.api;

import com.ouc.rpc.framework.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 消费者进行远程过程调用
 * @Author: Mr.Tong
 */
@Slf4j
@Component
public class ConsumerUseService {

    @Autowired
    private ProviderService providerService;


    public void testServiceA(String str) {
        String result = providerService.ServiceAMethod(str);
        log.info("testServiceA => {}", result);
    }


}
