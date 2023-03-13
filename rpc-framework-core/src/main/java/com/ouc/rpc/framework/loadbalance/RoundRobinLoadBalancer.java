package com.ouc.rpc.framework.loadbalance;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 轮询负载均衡器
 * @Author: Mr.Tong
 */
@Slf4j
public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public ExposeServiceModel getServiceInstance(List<ExposeServiceModel> serviceInstances) {
        int size = serviceInstances.size();
        int index = (counter.getAndIncrement() % size + size) % size;
        return serviceInstances.get(index);
    }
}
