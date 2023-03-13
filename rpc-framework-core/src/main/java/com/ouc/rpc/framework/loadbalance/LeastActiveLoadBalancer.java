package com.ouc.rpc.framework.loadbalance;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 最少活跃调用负载均衡器
 * @Author: Mr.Tong
 */
@Slf4j
public class LeastActiveLoadBalancer implements LoadBalancer {


    @Override
    public ExposeServiceModel getServiceInstance(List<ExposeServiceModel> serviceInstances) {
        return null;
    }

//
//    private final ConcurrentHashMap<String, AtomicInteger> activeCounts = new ConcurrentHashMap<>();
//
//    @Override
//    public ExposeServiceModel getServiceInstance(List<ExposeServiceModel> serviceInstances) {
//        ExposeServiceModel instance = null;
//        int minActive = Integer.MAX_VALUE;
//        // 遍历所有可用的服务实例
//        for (ExposeServiceModel exposeServiceModel : serviceInstances) {
//            AtomicInteger activeCount = activeCounts.computeIfAbsent(si.getInstanceId(), k -> new AtomicInteger());
//            int active = activeCount.get();
//            if (active < minActive) {
//                minActive = active;
//                instance = si;
//            }
//        }
//        if (instance != null) {
//            activeCounts.get(instance.getInstanceId()).incrementAndGet();
//        }
//        return instance;
//    }
}
