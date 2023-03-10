package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.registry.zk.ZkProviderServiceRegistry;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;


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
public class ExposeServiceModel implements Serializable, InitializingBean {

    /**
     * @Description: 提供者提供服务的ID
     */
    private String exposeServiceId;

    /**
     * @Description: 提供者暴露服务接口名称
     */
    private String exposeServiceName;

    /**
     * @Description: 提供者暴露服务接口实现类
     */
    private String exposeServiceImpl;

    /**
     * @Description: 提供者实例IP地址
     */
    private String providerInstanceIp;

    /**
     * @Description: 提供者实例端口
     */
    private String providerInstancePort;

    /*------------------------------------------------------------------------*/


    @Override
    public void afterPropertiesSet() throws Exception {
        // 属性被设置完毕 | 需要验证是否可以进行注册
        if (!ApplicationContextUtil.containsBean("rpcServerModel")) {// 未指定RPC端口 | 没有配置RPC服务器
            log.info("the rpc server port has not been specified");
            return;
        }
        if (!ApplicationContextUtil.containsBean("zooKeeperAddressModel")) {// 未指定ZK地址
            log.info("the zk address has not been specified");
            return;
        }
        if (!ApplicationContextUtil.containsBean("rpcApplicationModel")) {// 未配置应用信息
            log.info("the rpc application information has not been specified");
            return;
        }

        // 将服务信息注册到ZK服务器
        serviceRegistry();
    }

    private void serviceRegistry() {
        new ZkProviderServiceRegistry().serviceRegistry();
    }
}
