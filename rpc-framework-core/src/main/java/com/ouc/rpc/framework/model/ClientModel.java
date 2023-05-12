package com.ouc.rpc.framework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: RPC客户端模型
 * @Author: Mr.Tong
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientModel {
    /**
     * @Description: 客户端ID
     */
    private String clientId;

    /**
     * @Description: 客户端负载均衡方式
     */
    private String clientLoadBalance;
}
