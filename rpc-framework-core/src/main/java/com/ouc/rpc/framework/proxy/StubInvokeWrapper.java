package com.ouc.rpc.framework.proxy;

import com.ouc.rpc.framework.loadbalance.LoadBalancerFactory;
import com.ouc.rpc.framework.model.ExposeServiceModel;
import com.ouc.rpc.framework.model.ReferenceServiceModel;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.transport.RpcClient;
import com.ouc.rpc.framework.remote.transport.RpcResponseHandler;
import com.ouc.rpc.framework.util.SequenceUtil;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Description: 存根调用封装
 * @Author: Mr.Tong
 */
@Slf4j
public class StubInvokeWrapper {

    /**
     * @Description: RPC请求发送
     */
    public static Object remoteCall(ReferenceServiceModel referenceServiceModel, Method method, Object[] args) {


        // 获取可用的服务实例列表 | 缓存中选取
        Map<String, List<ExposeServiceModel>> services = referenceServiceModel.getServices();

        // 根据负载均衡器获得可用服务实例
        ExposeServiceModel serviceInstance = null;

        // 选择服务实例
        serviceInstance = LoadBalancerFactory.LOAD_BALANCE_SERVICE.getServiceInstance(services.get(referenceServiceModel.getProviderRpcFramework()));

        // 构造RPC请求
        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();

        rpcRequestMessage.setSequenceId(SequenceUtil.getSequenceId());
        rpcRequestMessage.setNetProtocol(serviceInstance.getNetworkProtocolType());
        rpcRequestMessage.setSerializationType(serviceInstance.getSerializerType());

        rpcRequestMessage.setInterfaceName(referenceServiceModel.getReferenceServiceName());
        rpcRequestMessage.setMethodName(method.getName());
        rpcRequestMessage.setArgTypes(method.getParameterTypes());
        rpcRequestMessage.setArgs(args);

        // 获取客户端和服务器端连接的channel，将请求参数写入
        Channel channel = RpcClient.getChannel(serviceInstance.getProviderInstanceIp(), serviceInstance.getProviderInstancePort());

        // 接收返回结果方式不同
//        if (serviceInstance.getNetworkProtocolType().equals("srpc")) {

        // 实现NIO线程和主线程的通信 | 通过Promise对象来接收结果 | 接收到结果之后判断Promise对象的状态
        DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
        RpcResponseHandler.PROMISES.put(rpcRequestMessage.getSequenceId(), promise);

        // 将数据写入信道，传输到服务器端
        channel.writeAndFlush(rpcRequestMessage);


        try {
            // 等待
            promise.await();
        } catch (InterruptedException e) {
            log.error("wait has exception: {}", e.getMessage());
        }

        // 判断结果
        if (promise.isSuccess()) {
            // 调用正常
            return promise.getNow();
        } else {
            // 调用失败
            log.error("invoke has exception: {}", promise.cause());
        }
//        } else {
//            // 将数据写入信道，传输到服务器端
//            channel.writeAndFlush(rpcRequestMessage);
//        }

        return null;
    }
}
