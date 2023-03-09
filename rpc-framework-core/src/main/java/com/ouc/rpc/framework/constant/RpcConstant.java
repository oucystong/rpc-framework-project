package com.ouc.rpc.framework.constant;

/**
 * @Description: RPC框架常用静态常量
 * @Author: Mr.Tong
 */
public class RpcConstant {
    /**
     * @Description: RPC配置文件
     */
    public static final String RPC_CONFIG_PATH = "rpc.properties";

    /**
     * @Description: ZK服务器地址的KEY
     */
    public static final String ZOOKEEPER_ADDRESS = "rpc.zk.address";

    /**
     * @Description: RPC端口
     */
    public static final String RPC_PORT = "rpc.port";

    /**
     * @Description: 应用名称
     */
    public static final String RPC_APPLICATION_NAME = "rpc.application.name";


    public static final String RPC_SERVICE_PROVIDER_ID = "rpc.service.provider.id";

    /**
     * @Description: 服务提供者提供的服务名称
     */
    public static final String RPC_SERVICE_PROVIDER_NAME = "rpc.service.provider.name";
    /**
     * @Description: 服务提供者提供的服务实现类
     */
    public static final String RPC_SERVICE_PROVIDER_IMPL = "rpc.service.provider.impl";


    /**
     * @Description: 服务注册时保存节点数据时使用的序列化方式
     */
    public static final String RPC_ZK_NODE_SERIALIZER = "rpc.zk.node.serializer";

    /**
     * @Description: ZK路径前缀
     */
    public static final String BASE_PATH_PREFIX = "/service/";
    /**
     * @Description: 服务提供者ZK路径后缀
     */
    public static final String BASE_PATH_SERVICE_PROVIDER_SUFFIX = "/provider";
    /**
     * @Description: 服务消费者ZK路径后缀
     */
    public static final String BASE_PATH_SERVICE_CONSUMER_SUFFIX = "/consumer";

    /**
     * @Description: ZK服务器默认地址
     */
    public static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";


}
