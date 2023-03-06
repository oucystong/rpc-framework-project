package com.ouc.rpc.framework.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ZK客户端
 * @Author: Mr.Tong
 */
@Slf4j
public class ZKClient {

    /**
     * @Description: 实现单例模式
     */
    private static ZKClient instance;

    private CuratorFramework zkClient;

    private ZKClient(String ip, String port) {
        log.info("start to connect to the zk server：[" + ip + ":" + port + "]");
        zkClient = CuratorFrameworkFactory.newClient(ip + ":" + port, new RetryNTimes(10, 5000));
        zkClient.start();
        log.info("successfully connected to the zk server：[" + ip + ":" + port + "]");
    }

    public static ZKClient getInstance(String ip, String port) {
        if (null == instance) {
            instance = new ZKClient(ip, port);
        }
        return instance;
    }

    /**
     * @Description: 检查ZooKeeper服务器中路径是否存在
     */
    private boolean exists(String path) {
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
            String[] paths = path.substring(1).split("/");
            String temp = "";
            for (String dir : paths) {
                temp += "/" + dir;
                if (!exists(temp)) {
                    try {
                        // 创建持久路径
                        zkClient.create().withMode(CreateMode.PERSISTENT).forPath(temp);
                        log.info("zk client create path successfully");
                    } catch (Exception e) {
                        log.error("zk client create path has exception: {}", e);
                    }
                }
            }
        }
    }

    /**
     * @Description: 在ZooKeeper服务器特定路径下保存节点
     */
    public void saveNode(String path, Object data) {
        try {
            if (exists(path)) {
                // 存在路径直接保存节点
//                zkClient.setData().forPath(path, Serializer.serialize(data));
                log.info("zk client save node successfully");
            } else {
                // 创建路径后保存节点
                String[] paths = path.substring(1).split("/");
                String temp = "";
                for (String dir : paths) {
                    temp += "/" + dir;
                    if (!exists(temp)) {
                        // 创建临时路径
                        zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(temp);
                    }
                }
                // 保存节点
//                zkClient.setData().forPath(path, Serializer.serialize(data));
                log.info("zk client save node successfully");
            }
        } catch (Exception e) {
            log.error("zk client save node has exception: {}", e);
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
    public Object getNodeData(String path) {
        // 路径不存在
        if (!exists(path)) {
            return null;
        }
        Object nodeData = null;
        // 路径存在
        try {
//            nodeData = Serializer.deserialize(zkClient.getData().forPath(path));
            log.info("zk client get node data successfully");
        } catch (Exception e) {
            log.error("zk client get node data has exception:{}", e);
        }
        return nodeData;
    }


    /**
     * @Description: 订阅ZooKeeper中子节点的变化
     */
    public void subscribeChildChange(String path, CuratorListener listener) {
        zkClient.getCuratorListenable().addListener(listener);
    }
}
