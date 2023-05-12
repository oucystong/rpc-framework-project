package com.ouc.rpc.framework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: ZK服务器地址模型
 * @Author: Mr.Tong
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ZooKeeperModel {

    /**
     * @Description: ZooKeeper注册中心的ID
     */
    private String zooKeeperId;


    /**
     * @Description: ZooKeeper注册中心的地址
     */
    private String zooKeeperAddress;

}
