package com.ouc.rpc.framework.spi.model;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class Run implements Sport{

    @Override
    public void sayHello() {
        System.out.println("Hello I am Run!");
    }


    @Override
    public void sayBye() {
        System.out.println("Bye I am Run!");
    }
}
