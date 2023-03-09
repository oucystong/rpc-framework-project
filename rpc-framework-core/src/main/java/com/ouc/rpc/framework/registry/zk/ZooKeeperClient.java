package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.constant.RpcConstant;
import com.ouc.rpc.framework.registry.ZKClient;
import com.ouc.rpc.framework.util.PropertiesFileUtil;
import com.ouc.rpc.framework.util.RpcCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 操作ZK注册中心的客户端工具类
 * @Author: Mr.Tong
 */

@Slf4j
public class ZooKeeperClient {
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    public static final String ZK_REGISTER_ROOT_PATH = "/my-rpc";
    private static final Map<String, List<String>> SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();
    private static final Set<String> REGISTERED_PATH_SET = ConcurrentHashMap.newKeySet();
//    private static CuratorFramework zkClient;

    private static ZooKeeperClient instance;

    private CuratorFramework zkClient;

    /**
     * Gets the children under a node
     *
     * @param rpcServiceName rpc service name eg:github.javaguide.HelloServicetest2version1
     * @return All child nodes under the specified node
     */
    public static List<String> getChildrenNodes(CuratorFramework zkClient, String rpcServiceName) {
        if (SERVICE_ADDRESS_MAP.containsKey(rpcServiceName)) {
            return SERVICE_ADDRESS_MAP.get(rpcServiceName);
        }
        List<String> result = null;
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName;
        try {
            result = zkClient.getChildren().forPath(servicePath);
            SERVICE_ADDRESS_MAP.put(rpcServiceName, result);
//            registerWatcher(rpcServiceName, zkClient);
        } catch (Exception e) {
            log.error("get children nodes for path [{}] fail", servicePath);
        }
        return result;
    }

    /**
     * Empty the registry of data
     */
    public static void clearRegistry(CuratorFramework zkClient, InetSocketAddress inetSocketAddress) {
        REGISTERED_PATH_SET.stream().parallel().forEach(p -> {
            try {
                if (p.endsWith(inetSocketAddress.toString())) {
                    zkClient.delete().forPath(p);
                }
            } catch (Exception e) {
                log.error("clear registry for path [{}] fail", p);
            }
        });
        log.info("All registered services on the server are cleared:[{}]", REGISTERED_PATH_SET.toString());
    }

    /**
     * @Description: 在ZooKeeper服务器特定路径下保存节点
     */
    public void saveNode(String path, byte[] serviceModel) {
        try {
            if (exists(path)) {
                // 存在路径直接保存节点
                zkClient.setData().forPath(path, serviceModel);
                log.info("zk client save node successfully");
            } else {
                // 创建路径后保存节点
                String[] paths = path.substring(1).split("/");
                String childPath = "";
                for (String dir : paths) {
                    childPath += "/" + dir;
                    if (!exists(childPath)) {
                        // 创建临时路径
                        createChildPath(childPath, CreateMode.EPHEMERAL);
                    }
                }
                // 保存节点
                zkClient.setData().forPath(path, serviceModel);
                log.info("zk client save node successfully");
            }
        } catch (Exception e) {
            log.error("zk client save node has exception: {}", e);
        }
    }


    /**
     * @Description: 检查ZooKeeper服务器中路径是否存在
     */
    public boolean exists(String path) {
        boolean result = false;
        try {
            result = zkClient.checkExists().forPath(path) != null;
            log.info("zk client check path, and the path is {}, the result is {}.", path, result);
        } catch (Exception e) {
            log.error("zk client check exists path has exception: {}", e);
        }
        return result;
    }

    /**
     * @Description: 在ZooKeeper服务器中创建一个路径
     */
    public void createPath(String path) {
        if (!exists(path)) {
            // /service/com.ouc.rpc.framework.provider.HelloService/provider
            String[] paths = path.substring(1).split("/");
            String childPath = "";
            // 遍历创建路径
            for (String dir : paths) {
                childPath += "/" + dir;
                if (!exists(childPath)) {
                    // 创建子路径
                    createChildPath(childPath, CreateMode.PERSISTENT);
                }
            }
            log.info("zk client create all path successfully");
        }
    }

    /**
     * @Description: ZK客户端构造方法
     */
    private ZooKeeperClient(String zookeeperAddress) {
        // 连接重试策略 | 最大重试次数为3 | 每次重试时间呈指数增加
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        // 创建ZK客户端
        zkClient = CuratorFrameworkFactory.builder().connectString(zookeeperAddress).retryPolicy(retryPolicy).build();
        log.info("zk client start connect the zk server");
        zkClient.start();
        log.info("zk successfully connect the zk server");
    }

    /**
     * @Description: 获取ZK客户端实例
     */
    public static ZooKeeperClient getInstance(String zookeeperAddress) {
        if (null == instance) {
            instance = new ZooKeeperClient(zookeeperAddress);
        }
        log.info("zk client has been build and return zk client");
        return instance;
    }


    /**
     * @Description: 创建临时/持久子路径
     */
    private void createChildPath(String childPath, CreateMode createMode) {
        try {
            // 创建持久路径
            zkClient.create().withMode(createMode).forPath(childPath);
            log.info("zk client create path: {} successfully", childPath);
        } catch (Exception e) {
            log.error("zk client create path has exception: {}", e);
        }
    }

    /**
     * Registers to listen for changes to the specified node
     *
     * @param rpcServiceName rpc service name eg:github.javaguide.HelloServicetest2version
     */
//    private static void registerWatcher(String rpcServiceName, CuratorFramework zkClient) throws Exception {
//        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName;
//        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, servicePath, true);
//        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
//            List<String> serviceAddresses = curatorFramework.getChildren().forPath(servicePath);
//            SERVICE_ADDRESS_MAP.put(rpcServiceName, serviceAddresses);
//        };
//        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
//        pathChildrenCache.start();
//    }
}
