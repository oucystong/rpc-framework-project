package com.ouc.rpc.framework.remote.transport;

import com.ouc.rpc.framework.remote.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: RPC响应消息处理器
 * @Author: Mr.Tong
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final Map<String, Promise<Object>> PROMISES = new ConcurrentHashMap<>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.info("get rpc response: {}", msg);
        // 拿到Promise对象 | 将结果进行存储 | 促使主线程取消阻塞状态获得结果
        Promise<Object> promise = PROMISES.remove(msg.getSequenceId());
        if (promise != null) {
            Boolean success = msg.getIsSuccess();
            if (success) {
                promise.setSuccess(msg.getResult());
            } else {
                promise.setFailure((Throwable) msg.getError());
            }
        }
    }
}
