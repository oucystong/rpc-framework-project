package com.ouc.rpc.framework.consumer.api;

import com.ouc.rpc.framework.api.ProviderService;
import com.ouc.rpc.framework.proxy.ProxyFactory;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Author: Mr.Tong
 */
@Service
public class ConsumerService {

    /**
     * @Description: 使用服务中的A方法
     */
    public String testAMethod(String param1) {
        // 调用远程方法 | 使用了RPC框架屏蔽了底层 | 所以像本地调用
        ProviderService providerService = ProxyFactory.getProxy(ProviderService.class);
        String result = providerService.ServiceAMethod(param1);
        return result;
    }


    /**
     * @Description: 使用服务中的B方法
     */
    public String testBMethod(String param1, String param2) {
        // 调用远程方法 | 使用了RPC框架屏蔽了底层 | 所以像本地调用
        ProviderService providerService = ProxyFactory.getProxy(ProviderService.class);
        String result = providerService.ServiceBMethod(param1, param2);
        return result;
    }


}
