package com.ouc.rpc.framework.config;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;


/**
 * @Description: 服务实例注册到ZK节点时需要的信息模型
 * @Author: Mr.Tong
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ServiceModel implements Serializable {

    /**
     * @Description: 服务的ID
     */
    private String serviceId;

    /**
     * @Description: 服务接口名称
     */
    private String serviceName;

    /**
     * @Description: 服务接口实现类
     */
    private String serviceImpl;

    /**
     * @Description: 服务实例的IP
     */
    private String serviceIp;

    /**
     * @Description: 服务实例的端口
     */
    private String servicePort;


}
