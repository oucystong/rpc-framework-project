package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.registry.zk.ZkProviderServiceRegistry;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;


import java.io.Serializable;
import java.util.Map;


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

    /**
     * @Description: 服务提供者支持的序列化方式
     */
    private String serializerType;

    /**
     * @Description: 服务提供者支持的网络协议
     */
    private String networkProtocolType;


    /*------------------------------------------------------------------------*/


    @Override
    public void afterPropertiesSet() throws Exception {
        // 属性被设置完毕 | 需要验证是否可以进行注册
        if (!ApplicationContextUtil.containsBean("applicationModel")) {// 未配置应用信息
            log.info("the rpc application information has not been specified");
            return;
        }
        if (!ApplicationContextUtil.containsBean("zooKeeperModel")) {// 未指定ZK地址
            log.info("the zk address has not been specified");
            return;
        }
        if (!ApplicationContextUtil.containsBean("serverModel")) {// 未指定RPC端口 | 没有配置RPC服务器
            log.info("the rpc server port has not been specified");
            return;
        }

        // 测试
//        Map<String, ExposeServiceModel> serviceBeansMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ExposeServiceModel.class);
//        System.out.println(serviceBeansMap);

        // 填充IP、端口信息、序列化信息、网络通信协议
        postProcess();

        // 将服务信息注册到ZK服务器
        serviceRegistry();
    }

    /**
     * @Description: 服务注册
     */
    private void serviceRegistry() {
        new ZkProviderServiceRegistry().serviceRegistry();
    }

    /**
     * @Description: 后置处理器-填充一些需要RPC框架获得的Bean信息
     */
    private void postProcess() {
        // 获取容器实例
        ServerModel serverModel = ApplicationContextUtil.getApplicationContext().getBean(ServerModel.class);
        // 获取所有需要暴露的服务
        Map<String, ExposeServiceModel> serviceBeansMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ExposeServiceModel.class);

        serviceBeansMap.forEach((s, exposeServiceModel) -> {
            // 对每一个Bean都填充IP信息和端口信息
            // 填充IP信息
            exposeServiceModel.setProviderInstanceIp(serverModel.getServerIp());
            // 填充端口信息
            exposeServiceModel.setProviderInstancePort(serverModel.getServerPort());
        });
    }
}
