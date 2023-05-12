package com.ouc.rpc.framework.alignment;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import org.apache.dubbo.common.extension.SPI;

/**
 * @Description: 服务获取及解析 | Dubbo框架的服务获取及解析
 * @Author: Mr.Tong
 */
@SPI("dubbo")  // 此dubbo指的是dubbo框架 | 一般一个RPC框架的服务地址设计单一并且其解析过程固定
public interface ServiceAccess {
    /**
     * @Description: 构建服务订阅地址 | 实现服务发现和服务订阅
     */
    String buildServicePath(String serviceName);

    /**
     * @Description: 解析订阅路径的子节点，即叶子结点（服务实例）的相关信息 | 将其对齐为ExposeServiceModel
     */
    ExposeServiceModel parseChildNodeOfServicePath(String childNode);

}
