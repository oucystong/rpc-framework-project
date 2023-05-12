package com.ouc.rpc.framework.remote.transport;

import com.ouc.rpc.framework.model.ExposeServiceModel;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description: RPC请求消息处理器
 * @Author: Mr.Tong
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {
        // 构造RPC响应消息
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();

        rpcResponseMessage.setNetProtocol(msg.getNetProtocol());
        rpcResponseMessage.setSerializationType(msg.getSerializationType());
        rpcResponseMessage.setSequenceId(msg.getSequenceId());

        try {
            // 获得请求消息ID
            log.info("the server receives the message:" + msg.getSequenceId());

            // 获取本地暴露的所有服务
            Map<String, ExposeServiceModel> exposeServiceModelMap = ApplicationContextUtil.getApplicationContext().getBeansOfType(ExposeServiceModel.class);
            ExposeServiceModel exposeServiceModel = null;

            // 匹配客户端请求的服务
            for (String key : exposeServiceModelMap.keySet()) {
                if (exposeServiceModelMap.get(key).getExposeServiceName().equals(msg.getInterfaceName())) {
                    exposeServiceModel = exposeServiceModelMap.get(key);
                    break;
                }
            }

            // 找不到对应的服务
            if (exposeServiceModel == null) {
                throw new RuntimeException("no service found: " + msg.getInterfaceName());
            }

            // 获取服务的实现类
            Object exposeServiceImpl = ApplicationContextUtil.getApplicationContext().getBean(Class.forName(exposeServiceModel.getExposeServiceImpl()));

            // 找不到实现类
            if (exposeServiceImpl == null) {
                throw new RuntimeException("no available service found: " + msg.getInterfaceName());
            }

            // 通过反射调用方法获取返回值
            Object result = exposeServiceImpl.getClass().getMethod(msg.getMethodName(), msg.getArgTypes()).invoke(exposeServiceImpl, msg.getArgs());

            // 将结果内容添加到RPC响应消息中
            rpcResponseMessage.setResult(result);
            rpcResponseMessage.setIsSuccess(true);
        } catch (Throwable e) {
            log.error("the server failed to process the request: {}", e.getCause().getMessage());
            rpcResponseMessage.setIsSuccess(false);
            rpcResponseMessage.setError(e);
        }

        // 将结果写入到Channel
        ctx.writeAndFlush(rpcResponseMessage);

    }
}
