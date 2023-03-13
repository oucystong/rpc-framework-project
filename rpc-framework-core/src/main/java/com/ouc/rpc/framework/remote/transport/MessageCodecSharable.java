package com.ouc.rpc.framework.remote.transport;

import com.ouc.rpc.framework.remote.Message;
import com.ouc.rpc.framework.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @Description: 消息编解码器
 * @Author: Mr.Tong
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    /**
     * @Description: 协议消息编码器
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        // 序列化消息
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器
        // 序列化消息
        byte[] serialize = serializer.serialize(msg);
        // 写入Buffer
        buffer.writeByte(serialize.length);
        buffer.writeBytes(serialize);

        out.add(buffer);
    }


    /**
     * @Description: 协议消息解码器
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 序列化消息
        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
        Serializer serializer = serializerExtensionLoader.getDefaultExtension(); // 序列化器

        // 读取数据长度
        int length = msg.readInt();
        // 将字节数组内容取出
        byte[] bytes = new byte[length];
        msg.readBytes(bytes, 0, length);
        // 反序列化
        Message deserialize = serializer.deserialize(bytes, Message.class);

        out.add(deserialize);
    }


}
