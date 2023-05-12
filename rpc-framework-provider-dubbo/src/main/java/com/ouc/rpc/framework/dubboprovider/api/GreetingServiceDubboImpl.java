package com.ouc.rpc.framework.dubboprovider.api;

import com.ouc.rpc.framework.api.model.Address;
import com.ouc.rpc.framework.api.model.Person;
import com.ouc.rpc.framework.api.service.GreetingService;
import org.springframework.stereotype.Service;

/**
 * @Description: Dubbo的服务实现GreetingService
 * @Author: Mr.Tong
 */

public class GreetingServiceDubboImpl implements GreetingService {

    @Override
    public String functionTypeServiceBase(String name) {
        return "dubbo hello, " + name;
    }

    @Override
    public Address functionTypeServiceCustom(Person person) {
        return null;
    }

    @Override
    public void consumerTypeServiceBase(String name) {

    }

    @Override
    public void consumerTypeServiceCustom(String person) {

    }

    @Override
    public String supplierTypeServiceBase() {
        return null;
    }

    @Override
    public Person supplierTypeServiceCustom() {
        return null;
    }
}
