package com.ouc.rpc.framework.boot;

import com.ouc.rpc.framework.registry.zk.ZkConsumerServiceRegistry;
import com.ouc.rpc.framework.registry.zk.ZkProviderServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;


/**
 * @Description: RPC框架的启动初始化类
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcFrameworkBoot implements InitializingBean {

    /**
     * @Description: 属性被全部注入完成之后回调当前方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 将服务提供者注册到ZK
        ZkProviderServiceRegistry zkProviderServiceRegistry = new ZkProviderServiceRegistry();
        zkProviderServiceRegistry.serviceRegistry();
        ZkConsumerServiceRegistry zkConsumerServiceRegistry = new ZkConsumerServiceRegistry();
        zkConsumerServiceRegistry.serviceRegistry();
    }
}
