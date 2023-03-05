package com.ouc.rpc.framework;

/**
 * @Description: 服务生产者提供的远程调用服务接口
 * @Author: Mr.Tong
 */
public interface ProviderService {

    /**
     * @Description: 服务A
     */
    public String ServiceAMethod(String str);


    /**
     * @Description: 服务B
     */
    public String ServiceBMethod(String str1, String str2);


}
