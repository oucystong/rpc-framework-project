package com.ouc.rpc.framework.remote.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description: Netty发送请求客户端
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcClient {

    /**
     * @Description: 多线程锁
     */
    private static final Object LOCK = new Object();
    /**
     * @Description: 使用一个Map存储每个主机对应Channel
     */
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();


    // 获取唯一的Channel对象
    public static Channel getChannel(String serverIp, String serverPort) {
        // 检查是否已经创建过长链接的Channel
        if (channelMap.containsKey(serverIp + "_" + serverPort)) {
            return channelMap.get(serverIp + "_" + serverPort);
        }
        synchronized (LOCK) { //  t2
            if (channelMap.containsKey(serverIp + "_" + serverPort)) { // t1
                return channelMap.get(serverIp + "_" + serverPort);
            }
            initChannel(serverIp, serverPort);
            return channelMap.get(serverIp + "_" + serverPort);
        }
    }

    // 初始化Channel方法
    private static void initChannel(String serverIp, String serverPort) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseHandler RPC_RESPONSE_HANDLER = new RpcResponseHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProcotolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_RESPONSE_HANDLER);
            }
        });
        try {
            Channel channel = bootstrap.connect(serverIp, Integer.parseInt(serverPort)).sync().channel();
            // 将channel添加到Map集合中
            channelMap.put(serverIp + "_" + serverPort, channel);
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }


}
