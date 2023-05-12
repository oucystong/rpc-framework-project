package com.ouc.rpc.framework.dubboprovider.api;

import com.ouc.rpc.framework.api.service.SecondService;

/**
 * @Description: Dubbo的服务实现SecondService
 * @Author: Mr.Tong
 */

public class SecondServiceDubboImpl implements SecondService {

    @Override
    public String sayHello(String name) {
        return "dubbo say hello " + name;
    }
}
