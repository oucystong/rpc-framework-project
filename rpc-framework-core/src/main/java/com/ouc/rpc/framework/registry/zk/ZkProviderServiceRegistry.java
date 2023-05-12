package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.model.*;
import com.ouc.rpc.framework.registry.ServiceRegistry;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @Description: 服务提供者服务注册实现类
 * @Author: Mr.Tong
 */
@Slf4j
public class ZkProviderServiceRegistry implements ServiceRegistry {

    @Override
    public void serviceRegistry() {

        // 获取所有需要注册的服务
        Map<String, ExposeServiceModel> serviceBeansMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ExposeServiceModel.class);

        // 获取ZK服务器地址
        ZooKeeperModel zooKeeperModel = ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperModel.class);
        String zooKeeperAddress = zooKeeperModel.getZooKeeperAddress();
        // 获取ZK客户端 | 单例模式
        ZooKeeperClient zooKeeperClient = ZooKeeperClient.getInstance(zooKeeperAddress);

        // 序列化节点数据 | 获取序列化器
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 默认的序列化器;


        serviceBeansMap.forEach((s, exposeServiceModel) -> {
            // 注册所有的服务
            // 获取ReferenceServiceModel Bean中所有属性值
            String exposeServiceName = exposeServiceModel.getExposeServiceName();
            String exposeServiceId = exposeServiceModel.getExposeServiceId();
            String providerInstanceIp = exposeServiceModel.getProviderInstanceIp();
            String providerInstancePort = exposeServiceModel.getProviderInstancePort();
            // 构造路径
            String basePath = RpcConstant.BASE_PATH_PREFIX
                    + exposeServiceId + "/"
                    + exposeServiceName
                    + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
            String path = basePath + "/" + providerInstanceIp + "_" + providerInstancePort;

            // 保存路径和节点
            zooKeeperClient.createPath(basePath); // 持久化路径 | 不会随着实例下线而消失

            // 保存节点数据
            zooKeeperClient.saveNode(path, serializer.serialize(exposeServiceModel));

            log.info("service published the zk server successfully");
        });
    }
}
