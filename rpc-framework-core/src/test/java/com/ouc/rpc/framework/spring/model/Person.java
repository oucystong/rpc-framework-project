package com.ouc.rpc.framework.spring.model;

import lombok.*;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person implements FactoryBean<Student> {

    private String personName;


    @Override
    public Student getObject() throws Exception {
        Student student = new Student();
        student.setCardNumber("12323");
        student.setUserName("xiaotong");
        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
