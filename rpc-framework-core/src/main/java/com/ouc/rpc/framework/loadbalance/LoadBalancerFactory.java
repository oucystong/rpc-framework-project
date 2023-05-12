package com.ouc.rpc.framework.loadbalance;

import com.ouc.rpc.framework.model.ClientModel;
import com.ouc.rpc.framework.model.ProxyModel;
import com.ouc.rpc.framework.proxy.ProxyService;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Description: 负载均衡服务工厂 | 获取某一个类型的负载均衡服务
 * @Author: Mr.Tong
 */
@Slf4j
public class LoadBalancerFactory {
    // 在类加载的时候确定动态代理方式
    public static LoadBalancer LOAD_BALANCE_SERVICE = null;

    // 在静态变量之后执行
    // 根据负载均衡策略选择一个服务实例 | 根据用户的策略进行选取 | 如果用户不进行配置 | 则使用默认值
    static {
        ExtensionLoader<LoadBalancer> extensionLoader = ExtensionLoader.getExtensionLoader(LoadBalancer.class);
        // 判断用户是否配置 | 若配置则使用用户配置的相关内容 | 否则使用默认的动态代理方式
        if (ApplicationContextUtil.containsBean("clientModel")) {// 存在用户配置
            ClientModel clientModel = ApplicationContextUtil.getApplicationContext().getBean("clientModel", ClientModel.class);
            LOAD_BALANCE_SERVICE = extensionLoader.getExtension(clientModel.getClientLoadBalance());
            log.info("use {} loadbalancer", clientModel.getClientLoadBalance());
        } else {// 使用默认的动态代理方式
            LOAD_BALANCE_SERVICE = extensionLoader.getDefaultExtension();
            log.info("use default loadbalancer");
        }
    }


}
