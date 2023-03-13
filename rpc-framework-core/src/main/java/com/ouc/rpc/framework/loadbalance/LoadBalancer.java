package com.ouc.rpc.framework.loadbalance;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import org.apache.dubbo.common.extension.SPI;

import java.util.List;

/**
 * @Description: 负载均衡器
 * @Author: Mr.Tong
 */
@SPI("random")
public interface LoadBalancer {

    /**
     * @Description: 从众多服务实例中选择一个服务实例进行调用
     */
    public ExposeServiceModel getServiceInstance(List<ExposeServiceModel> serviceInstances);
}
