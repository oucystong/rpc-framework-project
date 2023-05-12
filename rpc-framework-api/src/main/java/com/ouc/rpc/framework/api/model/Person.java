package com.ouc.rpc.framework.api.model;

import java.io.Serializable;

/**
 * @Description: 人员类
 * @Author: Mr.Tong
 */
public class Person implements Serializable {


    /**
     * @Description: 名字
     */
    private String name;
    /**
     * @Description: 年龄
     */
    private Integer age;

    /**
     * @Description: 性别
     */
    private String gender;

    /**
     * @Description: 地址
     */
    private Address address;

    public Person() {
    }

    public Person(String name, Integer age, String gender, Address address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address=" + address +
                '}';
    }
}
