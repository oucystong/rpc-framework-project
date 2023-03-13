package com.ouc.rpc.framework.proxy;

import cn.hutool.core.util.IdUtil;
import com.ouc.rpc.framework.loadbalance.LoadBalancer;
import com.ouc.rpc.framework.model.ExposeServiceModel;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.transport.RpcClient;
import com.ouc.rpc.framework.serialization.Serializer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description: Cglib方法执行拦截增强器
 * @Author: Mr.Tong
 */
@Slf4j
public class CglibMethodInterceptor implements MethodInterceptor {


    private ReferenceServiceModel referenceServiceModel;

    public CglibMethodInterceptor(ReferenceServiceModel referenceServiceModel) {
        this.referenceServiceModel = referenceServiceModel;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        // 确定方法名称、方法参数、方法参数类型
        return invoke(method.getName(), args, method.getParameterTypes());
    }

    /**
     * @Description: 方法调用
     */
    private Object invoke(String methodName, Object[] args, Class[] argTypes) {
        // 接口方法执行
        return remoteCall(referenceServiceModel, methodName, argTypes, args);
    }

    /**
     * @Description: RPC请求发送
     */
    private Object remoteCall(ReferenceServiceModel referenceServiceModel, String methodName, Class[] argTypes, Object[] args) {
        // 构造请求参数
        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();

        rpcRequestMessage.setRequestId(IdUtil.simpleUUID());
        rpcRequestMessage.setMethodName(methodName);
        rpcRequestMessage.setArgTypes(argTypes);
        rpcRequestMessage.setArgs(args);
        rpcRequestMessage.setInterfaceName(referenceServiceModel.getReferenceServiceName());

        // 获取可用的服务实例列表
        List<ExposeServiceModel> services = referenceServiceModel.getServices();

        // 根据负载均衡策略选择一个服务实例
        ExtensionLoader<LoadBalancer> loadBalancerExtensionLoader = ExtensionLoader.getExtensionLoader(LoadBalancer.class);
        LoadBalancer loadBalancer = loadBalancerExtensionLoader.getDefaultExtension();// 负载均衡器

        // 根据负载均衡器获得可用服务实例
        ExposeServiceModel serviceInstance = loadBalancer.getServiceInstance(services);

        // 获取客户端的channel，将请求参数写入
        Channel channel = RpcClient.getChannel(serviceInstance.getProviderInstanceIp(), serviceInstance.getProviderInstancePort());

        // 将数据写入信道，传输到服务器端
        channel.writeAndFlush(rpcRequestMessage);


        return null;
    }


}
