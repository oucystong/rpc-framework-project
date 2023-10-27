package com.ouc.rpc.framework.provider.api;

import com.ouc.rpc.framework.api.model.Address;
import com.ouc.rpc.framework.api.model.Person;
import com.ouc.rpc.framework.api.service.GreetingService;
import org.springframework.stereotype.Service;

/**
 * @Description: 服务生产者提供的服务实现
 * @Author: Mr.Tong
 */
@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String functionTypeServiceBase(String name) {
//        return "srpc framework , " + name;
//        return name;
//        return "the message from "
//        return name + " message from srpc framework!";
        return name;
    }

    @Override
    public Address functionTypeServiceCustom(Person person) {
        Address address = new Address();
        address.setStreet(person.getAddress().getStreet());
        address.setCardNumber(person.getAddress().getCardNumber());
        return address;
    }

    @Override
    public void consumerTypeServiceBase(String name) {
        System.out.println("receive name: " + name);
    }

    @Override
    public void consumerTypeServiceCustom(String person) {
        System.out.println("receive person: " + person);
    }

    @Override
    public String supplierTypeServiceBase() {
        return "hello world";
    }

    @Override
    public Person supplierTypeServiceCustom() {
        Person person = new Person();
        person.setName("coder");
        person.setAge(26);
        person.setGender("男");
        person.setAddress(new Address("青岛市中国海洋大学", 159));
        return person;
    }
}
