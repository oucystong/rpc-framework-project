package com.ouc.rpc.framework.remote.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Netty接收请求服务端
 * @Author: Mr.Tong
 */
@Slf4j
public class RpcServer extends Thread {


    private String port;

    public RpcServer(String port) {
        this.port = port;
    }

    @Override
    public void run() {
        // 负责处理请求
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 负责实际读写工作
        NioEventLoopGroup worker = new NioEventLoopGroup();
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        // 协议编解码
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        // 请求消息处理器
        RpcRequestHandler RPC_REQUEST_HANDLER = new RpcRequestHandler();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(RPC_REQUEST_HANDLER);
                }
            });
            // 绑定端口
            Channel channel = serverBootstrap.bind(Integer.parseInt(port)).sync().channel();
            log.info("server started successfully, listened [" + port + "] port");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server failed to start，listened [" + port + "] port", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
