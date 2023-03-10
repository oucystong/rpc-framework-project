package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.registry.zk.ServiceChangeListener;
import com.ouc.rpc.framework.registry.zk.ZkConsumerServiceRegistry;
import com.ouc.rpc.framework.registry.zk.ZkProviderServiceRegistry;
import com.ouc.rpc.framework.registry.zk.ZooKeeperClient;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import com.ouc.rpc.framework.util.PropertiesFileUtil;
import com.ouc.rpc.framework.util.ReferenceServiceUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description: 客户端在服务发现时需要引用的信息模型
 * @Author: Mr.Tong
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ReferenceServiceModel implements Serializable, InitializingBean {

    /**
     * @Description: 消费者引用服务的ID
     */
    private String referenceServiceId;

    /**
     * @Description: 消费者引用服务接口名称
     */

    private String referenceServiceName;

    /**
     * @Description: 消费者引用服务接口实现类
     */
    private String referenceServiceImpl;

    /**
     * @Description: 消费者实例IP地址
     */
    private String consumerInstanceIp;

    /*------------------------------------------------------------------------*/

    /**
     * @Description: 存储可用的服务实例
     */
    private transient List<ExposeServiceModel> services;


    @Override
    public void afterPropertiesSet() throws Exception {
        // 检查客户端是否正确配置
        if (!ApplicationContextUtil.containsBean("rpcClientModel")) {// 没有配置RPC客户端
            log.info("the client model has not been specified");
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
        // 初始化
        init();
    }

    public void init() throws Exception {
        // 将服务信息注册到ZK服务器
        serviceRegistry();

        // 服务发现 | 所有提供的服务实例信息都存储到services变量中
        getReferences();

        // 将引用进行缓存
        referenceCache();

        // 订阅服务变化
        subscribeServiceChange();
    }


    private void referenceCache() {
        ReferenceServiceUtil.put(this);
    }

    private void serviceRegistry() {
        new ZkConsumerServiceRegistry().serviceRegistry();
    }


    public void getReferences() throws Exception {
        // 构造服务提供者路径
        String path = RpcConstant.BASE_PATH_PREFIX + this.referenceServiceName + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
        log.info("start get reference service");
        // 存储可用服务实例结果
        this.services = new ArrayList<>();
        // 获取ZK客户端实例
        ZooKeeperClient zkClient = ZooKeeperClient.getInstance(ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperAddressModel.class).getZooKeeperAddress());
        // 获得可用服务实例子节点
        List<String> childNodes = zkClient.getChildNodes(path);
        // 获取序列化器
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器

        for (String node : childNodes) {
            ExposeServiceModel exposeServiceModel = serializer.deserialize(zkClient.getNodeData(path + "/" + node), ExposeServiceModel.class);
            this.services.add(exposeServiceModel);
        }
        log.info("get reference service successfully, and the services: {}", this.services);
    }


    /**
     * @Description: 订阅服务的变化 | 变化之后触发回调函数
     */
    private void subscribeServiceChange() throws Exception {
        // 获取ZK客户端
        ZooKeeperClient zkClient = ZooKeeperClient.getInstance(ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperAddressModel.class).getZooKeeperAddress());
        // 构造监听路径
        String childPath = RpcConstant.BASE_PATH_PREFIX + this.referenceServiceName + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
        // 订阅子目录变化
        zkClient.subscribeChildChange(childPath, this.referenceServiceName);
    }

}
