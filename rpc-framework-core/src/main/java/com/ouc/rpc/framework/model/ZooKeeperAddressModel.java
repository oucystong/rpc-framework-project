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
public class ZooKeeperAddressModel {

    private String zooKeeperAddress;

}
