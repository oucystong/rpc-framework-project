package com.ouc.rpc.framework.api.service;

import com.ouc.rpc.framework.api.model.Address;
import com.ouc.rpc.framework.api.model.Person;

/**
 * @Description: 服务生产者提供的远程调用服务接口 | 函数式、消费式、供给式 | 三种类型的服务
 * @Author: Mr.Tong
 */
public interface GreetingService {

    /**
     * @Description: 服务A-函数式类型-基本类型
     */
    public String functionTypeServiceBase(String name);

    /**
     * @Description: 服务A-函数式类型-自定义类型
     */
    public Address functionTypeServiceCustom(Person person);

    /**
     * @Description: 服务B-消费式类型-基本类型
     */
    public void consumerTypeServiceBase(String name);

    /**
     * @Description: 服务B-消费式类型-自定义类型
     */
    public void consumerTypeServiceCustom(String person);

    /**
     * @Description: 服务C-消费式类型-基本类型
     */
    public String supplierTypeServiceBase();

    /**
     * @Description: 服务C-消费式类型-自定义类型
     */
    public Person supplierTypeServiceCustom();


}
