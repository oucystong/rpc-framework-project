package com.ouc.rpc.framework.registry.zk;

import com.ouc.rpc.framework.util.ReferenceServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     * @Description: ZK客户端构造方法
     */
    private ZooKeeperClient(String zookeeperAddress) {
        // 连接重试策略 | 最大重试次数为3 | 每次重试时间呈指数增加
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        // 创建ZK客户端
        zkClient = CuratorFrameworkFactory.builder().connectString(zookeeperAddress).sessionTimeoutMs(1000).retryPolicy(retryPolicy).build();
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
     * @Description: 获取特定路径下的子节点
     */
    public List<String> getChildNodes(String path) {
        // 路径不存在则返回空列表
        if (!exists(path)) {
            return new ArrayList<>();
        }
        List<String> childNodes = new ArrayList<>();
        // 路径存在
        try {
            childNodes = zkClient.getChildren().forPath(path);
            log.info("zk client get child nodes successfully");
        } catch (Exception e) {
            log.error("zk client get child nodes has exception: {}", e);
        }
        return childNodes;
    }

    /**
     * @Description: 获取ZooKeeper服务器中节点的数据
     */
    public byte[] getNodeData(String path) {
        // 路径不存在
        if (!exists(path)) {
            return null;
        }
        byte[] nodeData = null;
        // 路径存在
        try {
            nodeData = zkClient.getData().forPath(path);
            log.info("zk client get node data successfully");
        } catch (Exception e) {
            log.error("zk client get node data has exception:{}", e);
        }
        return nodeData;
    }


    /**
     * @Description: 订阅ZooKeeper中路径和数据的变化 | 添加监听器 | 回调函数
     */
    public void subscribeChildChange(String childPath, String referenceServiceName) throws Exception {
        log.info("start subscription service instance: [" + childPath + "]");
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, childPath, true);
        pathChildrenCache.getListenable().addListener((client, event) -> {
            // 监听器回调函数
            switch (event.getType()) {
                case CHILD_ADDED:
                    ReferenceServiceUtil.get(referenceServiceName).getReferences();
                    log.info("service instance changed: ==> instance add ==> {}", pathChildrenCache.getCurrentData());
                    break;
                case CHILD_REMOVED:
                    ReferenceServiceUtil.get(referenceServiceName).getReferences();
                    log.info("service instance changed: ==> instance remove ==> {}", pathChildrenCache.getCurrentData());
                    break;
                case CHILD_UPDATED:
                    ReferenceServiceUtil.get(referenceServiceName).getReferences();
                    log.info("service instance changed: ==> instance update ==> {}", pathChildrenCache.getCurrentData());
                    break;
                default:
                    break;
            }
        });
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    }

}
