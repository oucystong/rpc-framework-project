package com.ouc.rpc.framework.provider.api;

import com.ouc.rpc.framework.api.service.SecondService;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class SecondServiceImpl implements SecondService {

    @Override
    public String sayHello(String name) {
        return "second hello, " + name;
    }
}
