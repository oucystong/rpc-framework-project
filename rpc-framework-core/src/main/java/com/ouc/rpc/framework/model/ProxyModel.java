package com.ouc.rpc.framework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 确定用户配置的动态代理方式
 * @Author: Mr.Tong
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ProxyModel {

    /**
     * @Description: 动态代理方式ID
     */
    private String proxyId;


    /**
     * @Description: 动态代理方式
     */
    private String proxyType;

}
