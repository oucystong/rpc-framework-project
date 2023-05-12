package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.alignment.ServiceAccess;
import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.proxy.ProxyFactory;
import com.ouc.rpc.framework.registry.zk.ZkConsumerServiceRegistry;
import com.ouc.rpc.framework.registry.zk.ZooKeeperClient;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import com.ouc.rpc.framework.util.ReferenceServiceUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ReferenceServiceModel implements Serializable, FactoryBean<Object>, InitializingBean {

    /**
     * @Description: 消费者引用服务的ID
     */
    private String referenceServiceId;

    /**
     * @Description: 消费者引用服务接口名称
     */

    private String referenceServiceName;


    /**
     * @Description: 消费者实例IP地址
     */
    private String consumerInstanceIp;

    /**
     * @Description: 当前引用的服务基于何种RPC框架进行开发
     */
    private String providerRpcFramework;


    /*------------------------------------------------------------------------*/

    /**
     * @Description: 存储可用的服务实例 | key代表当前引用的服务由何种类型的RPC框架提供 | value是可用的服务实例
     */
//    private transient List<ExposeServiceModel> services;
    private transient Map<String, List<ExposeServiceModel>> services;


    @Override
    public void afterPropertiesSet() throws Exception {
        // 检查客户端是否正确配置
        if (!ApplicationContextUtil.containsBean("zooKeeperModel")) {// 未指定ZK地址
            log.info("the zk address has not been specified");
            return;
        }

        if (!ApplicationContextUtil.containsBean("applicationModel")) {// 未配置应用信息
            log.info("the rpc application information has not been specified");
            return;
        }

//        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);
//        System.out.println(referenceServicesMap);

        // 初始化
        init();
    }

    public void init() throws Exception {

        // 填充IP信息
        postProcess();

        // 将服务信息注册到ZK服务器 | 服务消费者注册
        serviceRegistry();

        // 服务发现 | 所有提供的服务实例信息都存储到services变量中
        serviceDiscovery();

        // 将引用进行缓存
        serviceCache();

        // 订阅服务变化
        serviceSubscribe();

    }


    private void serviceCache() {
        // 获取所有需要引用的服务
        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);
        // 将每一个引用的服务进行缓存
        referenceServicesMap.forEach((s, referenceServiceModel) -> ReferenceServiceUtil.put(referenceServiceModel));
    }

    /**
     * @Description: 服务注册
     */
    private void serviceRegistry() {
        new ZkConsumerServiceRegistry().serviceRegistry();
    }


    public void serviceDiscovery() throws Exception {
        // 获取所有的服务
        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);
        // 获取ZK客户端实例
        ZooKeeperClient zkClient = ZooKeeperClient.getInstance(ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperModel.class).getZooKeeperAddress());
        // 获取序列化器
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器

        // 获取发现
        referenceServicesMap.forEach((s, referenceServiceModel) -> {

            // srpc提供服务 | 实现其服务发现过程
            if (referenceServiceModel.getProviderRpcFramework().equals("srpc")) {
                // 对引用的每一个服务都构造服务提供者路径
                String path = RpcConstant.BASE_PATH_PREFIX + referenceServiceModel.getReferenceServiceId() + "/" + referenceServiceModel.getReferenceServiceName() + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
                log.info("start get reference service");
                // 存储可用服务实例结果 | 存一个空列表
//                referenceServiceModel.setServices(new ArrayList<>());
                referenceServiceModel.setServices(new HashMap<>());
                referenceServiceModel.getServices().put("srpc", new ArrayList<ExposeServiceModel>());
                // 获得可用服务实例子节点
                List<String> childNodes = zkClient.getChildNodes(path);
                // 将注册的服务信息进行存储
                for (String node : childNodes) {
                    ExposeServiceModel exposeServiceModel = serializer.deserialize(zkClient.getNodeData(path + "/" + node), ExposeServiceModel.class);
                    referenceServiceModel.getServices().get("srpc").add(exposeServiceModel);
                }
                log.info("get reference service successfully, and the services: {}", referenceServiceModel.getServices());
            } else { // 其他RPC框架提供服务 | 实现其服务发现过程

                // 确定协议对齐方式
                ExtensionLoader<ServiceAccess> serviceAccessExtensionLoader = ExtensionLoader.getExtensionLoader(ServiceAccess.class);
                ServiceAccess serviceAccess = serviceAccessExtensionLoader.getExtension(referenceServiceModel.getProviderRpcFramework());

                // 服务路径
                String path = serviceAccess.buildServicePath(referenceServiceModel.referenceServiceName);

                // 空容器
                referenceServiceModel.setServices(new HashMap<>());
                referenceServiceModel.getServices().put(referenceServiceModel.getProviderRpcFramework(), new ArrayList<ExposeServiceModel>());

                // 获得可用服务实例子节点
                List<String> childNodes = zkClient.getChildNodes(path);

                // 将注册的服务信息进行存储
                for (String node : childNodes) {
                    ExposeServiceModel exposeServiceModel = serviceAccess.parseChildNodeOfServicePath(node);
                    referenceServiceModel.getServices().get(referenceServiceModel.getProviderRpcFramework()).add(exposeServiceModel);
                }
            }
        });
    }

    // 更新引用服务的可用实例缓存
    public void updateReferences() throws Exception {
        // 获取ZK客户端实例
        ZooKeeperClient zkClient = ZooKeeperClient.getInstance(ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperModel.class).getZooKeeperAddress());

        // 获取序列化器
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器

        if (this.providerRpcFramework.equals("srpc")) {
            String path = RpcConstant.BASE_PATH_PREFIX + this.getReferenceServiceId() + "/" + this.getReferenceServiceName() + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;

            this.setServices(new HashMap<>());
            this.getServices().put("srpc", new ArrayList<ExposeServiceModel>());

            // 获得可用服务实例子节点
            List<String> childNodes = zkClient.getChildNodes(path);
            // 将注册的服务信息进行存储
            for (String node : childNodes) {
                ExposeServiceModel exposeServiceModel = serializer.deserialize(zkClient.getNodeData(path + "/" + node), ExposeServiceModel.class);
                this.getServices().get("srpc").add(exposeServiceModel);
            }

        } else {
            ExtensionLoader<ServiceAccess> serviceAccessExtensionLoader = ExtensionLoader.getExtensionLoader(ServiceAccess.class);
            ServiceAccess serviceAccess = serviceAccessExtensionLoader.getExtension(this.providerRpcFramework);


            // 服务路径
            String path = serviceAccess.buildServicePath(this.referenceServiceName);

            // 空容器
            this.setServices(new HashMap<>());
            this.getServices().put(this.providerRpcFramework, new ArrayList<ExposeServiceModel>());

            // 获得可用服务实例子节点
            List<String> childNodes = zkClient.getChildNodes(path);

            // 将注册的服务信息进行存储
            for (String node : childNodes) {
                ExposeServiceModel exposeServiceModel = serviceAccess.parseChildNodeOfServicePath(node);
                this.getServices().get(this.providerRpcFramework).add(exposeServiceModel);
            }
        }
    }


    /**
     * @Description: 订阅服务的变化 | 变化之后触发回调函数
     */
    private void serviceSubscribe() throws Exception {
        // 获取ZK客户端
        ZooKeeperClient zkClient = ZooKeeperClient.getInstance(ApplicationContextUtil.getApplicationContext().getBean(ZooKeeperModel.class).getZooKeeperAddress());

        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);

        referenceServicesMap.forEach((s, referenceServiceModel) -> {
            if (referenceServiceModel.getProviderRpcFramework().equals("srpc")) {
                // 构造监听路径
                String childPath = RpcConstant.BASE_PATH_PREFIX + referenceServiceModel.getReferenceServiceId() + "/" + referenceServiceModel.getReferenceServiceName() + RpcConstant.BASE_PATH_SERVICE_PROVIDER_SUFFIX;
                // 订阅子目录变化
                try {
                    zkClient.subscribeChildChange(childPath, referenceServiceModel.getReferenceServiceName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                ExtensionLoader<ServiceAccess> serviceAccessExtensionLoader = ExtensionLoader.getExtensionLoader(ServiceAccess.class);
                ServiceAccess serviceAccess = serviceAccessExtensionLoader.getExtension(referenceServiceModel.getProviderRpcFramework());

                // 服务路径
                String path = serviceAccess.buildServicePath(referenceServiceModel.referenceServiceName);

                // 订阅子目录变化
                try {
                    zkClient.subscribeChildChange(path, referenceServiceModel.getReferenceServiceName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // 生成代理对象
    @Override
    public Object getObject() throws Exception {
        Class<?> clazz = getObjectType();
        // 生成接口的代理对象
        return ProxyFactory.PROXY_SERVICE.getProxy(clazz, this);
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(referenceServiceName);
        } catch (ClassNotFoundException e) {
            log.error("the reference service name has not been specified", e.getMessage());
        }
        return null;
    }


    /**
     * @Description: 后置处理器-填充一些需要RPC框架获得的Bean信息
     */
    private void postProcess() throws UnknownHostException {
        // 填充IP信息
        Map<String, ReferenceServiceModel> referenceServicesMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ReferenceServiceModel.class);
        // 遍历填充
        referenceServicesMap.forEach((s, referenceServiceModel) -> {
            try {
                // 填充IP信息
                referenceServiceModel.setConsumerInstanceIp(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
