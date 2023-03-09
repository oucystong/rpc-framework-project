package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.config.ServiceModel;
import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.registry.ServiceRegistry;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.PropertiesFileUtil;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @Description: 服务注册接口
 * @Author: Mr.Tong
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry, InitializingBean {

    @Override
    public void serviceRegistry() {
        // 获取配置文件
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConstant.RPC_CONFIG_PATH);
        String rpcServiceProviderName = properties.getProperty(RpcConstant.RPC_SERVICE_PROVIDER_NAME);
        String rpcPort = properties.getProperty(RpcConstant.RPC_PORT);

        // 构造路径
        String basePath = RpcConstant.BASE_PATH_PREFIX + rpcServiceProviderName + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
        String path = null;
        try {
            path = basePath + "/" + InetAddress.getLocalHost().getHostAddress() + "_" + rpcPort;
        } catch (UnknownHostException e) {
            log.error("get local host has exception: {}", e.getMessage());
        }
        // 获取ZK服务器地址
        String zookeeperAddress = properties != null && properties.getProperty(RpcConstant.ZOOKEEPER_ADDRESS) != null && RpcCommonUtil.isCorrectIpPort(properties.getProperty(RpcConstant.ZOOKEEPER_ADDRESS)) ? properties.getProperty(RpcConstant.ZOOKEEPER_ADDRESS) : RpcConstant.DEFAULT_ZOOKEEPER_ADDRESS;

        // 获取ZK客户端
        ZooKeeperClient zooKeeperClient = ZooKeeperClient.getInstance(zookeeperAddress);

        // 保存路径和节点
        zooKeeperClient.createPath(basePath); // 持久化路径 | 不会随着实例下线而消失

        // 序列化节点数据
        String serializerKey = properties.getProperty(RpcConstant.RPC_ZK_NODE_SERIALIZER);
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getExtension(serializerKey); // 序列化器

        // 获取并通过建造者模式构造节点数据
        String serviceId = properties.getProperty(RpcConstant.RPC_SERVICE_PROVIDER_ID);
        String serviceName = rpcServiceProviderName;
        String serviceImpl = properties.getProperty(RpcConstant.RPC_SERVICE_PROVIDER_IMPL);
        String serviceIp = null;
        try {
            serviceIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get local host has exception: {}", e.getMessage());
        }
        String servicePort = rpcPort;

        // 序列化并保存到节点
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setServiceId(serviceId);
        serviceModel.setServiceName(serviceName);
        serviceModel.setServiceImpl(serviceImpl);
        serviceModel.setServiceIp(serviceIp);
        serviceModel.setServicePort(servicePort);
        zooKeeperClient.saveNode(path, serializer.serialize(serviceModel));

        log.info("service published the zk server successfully");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("======");
        serviceRegistry();
    }
}
