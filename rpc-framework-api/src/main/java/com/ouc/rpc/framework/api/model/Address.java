package com.ouc.rpc.framework.api.model;

import java.io.Serializable;

/**
 * @Description: 地址类
 * @Author: Mr.Tong
 */
public class Address implements Serializable {

    /**
     * @Description: 街道
     */
    private String street;

    /**
     * @Description: 门牌号
     */
    private Integer cardNumber;

    public Address() {
    }

    public Address(String street, Integer cardNumber) {
        this.street = street;
        this.cardNumber = cardNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
