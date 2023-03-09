package com.ouc.rpc.framework.spring.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Slf4j
public class User implements InitializingBean {
    private String username;
    private String password;

    public User() {
        log.info("无参构造函数执行");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        log.info("全参构造函数执行");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        log.info("依赖注入userName属性值 {}", username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        log.info("依赖注入passWord属性值 {}", password);
        this.password = password;
    }

    public void initMethod() {
        log.info("初始化方法");
    }

    public void destroyMethod() {
        log.info("销毁方法");
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * @Description: 依赖注入完成之后，即所有的属性值都设置完成之后会自动回调该函数，但是如果有后置处理器，则在后置处理器之后，但是肯定在初始化方法之前
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("属性设置完毕之后执行");
    }
}