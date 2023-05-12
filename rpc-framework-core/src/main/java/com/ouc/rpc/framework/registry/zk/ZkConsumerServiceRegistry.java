package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.model.ApplicationModel;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.model.ZooKeeperModel;
import com.ouc.rpc.framework.registry.ServiceRegistry;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.Map;
import java.util.function.BiConsumer;


/**
 * @Description: 服务消费者服务注册实现类
 * @Author: Mr.Tong
 */
@Slf4j
public class ZkConsumerServiceRegistry implements ServiceRegistry {

    @Override
    public void serviceRegistry() {

        // 或许所有需要引用的服务
        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);

        // 获取ZK服务器地址
        ZooKeeperModel zooKeeperModel = ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperModel.class);
        String zooKeeperAddress = zooKeeperModel.getZooKeeperAddress();
        // 获取ZK客户端
        ZooKeeperClient zooKeeperClient = ZooKeeperClient.getInstance(zooKeeperAddress);

        // 序列化节点数据
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器


        // 循环注册
        referenceServicesMap.forEach((s, referenceServiceModel) -> {

            // 获取ReferenceServiceModel Bean中所有属性值
            String referenceServiceName = referenceServiceModel.getReferenceServiceName();
            String referenceServiceId = referenceServiceModel.getReferenceServiceId();
            String consumerInstanceIp = referenceServiceModel.getConsumerInstanceIp();

            // 构造路径
            String basePath = RpcConstant.BASE_PATH_PREFIX
                    + referenceServiceId + "/"
                    + referenceServiceName
                    + RpcConstant.BASE_PATH_SERVICE_CONSUMER_SUFFIX;
            String path = basePath + "/" + consumerInstanceIp;

            // 保存路径和节点
            zooKeeperClient.createPath(basePath); // 持久化路径 | 不会随着实例下线而消失

            // 保存节点数据
            zooKeeperClient.saveNode(path, serializer.serialize(referenceServiceModel));

            log.info("service published the zk server successfully");
        });
    }
}
