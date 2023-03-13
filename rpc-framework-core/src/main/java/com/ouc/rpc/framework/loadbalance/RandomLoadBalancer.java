package com.ouc.rpc.framework.loadbalance;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @Description: 随机负载均衡器
 * @Author: Mr.Tong
 */
@Slf4j
public class RandomLoadBalancer implements LoadBalancer {
    private final Random random = new Random();

    @Override
    public ExposeServiceModel getServiceInstance(List<ExposeServiceModel> serviceInstances) {
        int size = serviceInstances.size();
        int index = random.nextInt(size);
        return serviceInstances.get(index);
    }
}
