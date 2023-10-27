package com.ouc.rpc.framework.remote.transport.codec;

import com.ouc.rpc.framework.alignment.NetProtocolAlignment;

import com.ouc.rpc.framework.remote.Message;
import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.enums.SerializerEnum;
import com.ouc.rpc.framework.serialization.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Description: 通用协议编码的过程
 * @Author: Mr.Tong
 */
public class Encode extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 开辟空间
        ByteBuf buffer = ctx.alloc().buffer();

        if (msg.getNetProtocol().equals("srpc")) {
            // sRPC网络通信协议编码
            // 4个字节的魔数
            buffer.writeBytes(new byte[]{'s', 'r', 'p', 'c'});
            // 1个字节的版本号
            buffer.writeByte(1);
            // 1个字节的序列化方式
            buffer.writeByte(SerializerEnum.idOfType(msg.getSerializationType()));
            // 2个字节的消息类型
            buffer.writeByte(msg.getMessageType());
            buffer.writeByte(0xff);
            // 4个字节的RPC请求序列号
            buffer.writeInt(msg.getSequenceId().intValue());
            // 序列化消息
            ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
            Serializer serializer = serializerExtensionLoader.getExtension(msg.getSerializationType());
            byte[] serialize = serializer.serialize(msg);
            // 4个字节有效载荷数据的长度
            buffer.writeInt(serialize.length);
            // 写入有效载荷数据
            buffer.writeBytes(serialize);
        } else { // 调用其他RPC框架的服务，则使用协议对齐方案

            ExtensionLoader<NetProtocolAlignment> netProtocolAlignmentExtensionLoader = ExtensionLoader.getExtensionLoader(NetProtocolAlignment.class);
            NetProtocolAlignment netProtocolAlignment = netProtocolAlignmentExtensionLoader.getExtension(msg.getNetProtocol());
            Integer originId = netProtocolAlignment.getOriginIdOfSerializationType(msg.getSerializationType());
            // 序列化器 | 通过指定的序列化方式可以得到sRPC框架的序列化器
            Integer targetId = netProtocolAlignment.getTargetIdOfSerializationType(msg.getSerializationType());
            ExtensionLoader<Serializer> extensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
            Serializer serializer = extensionLoader.getExtension(SerializerEnum.typeOfId(targetId));

            byte[] encodeResult = netProtocolAlignment.encodeProtocolAlignment((RpcRequestMessage) msg, originId, serializer);
            buffer.writeBytes(encodeResult);
        }
        // 向开辟的空间写入协议数据包
        out.writeBytes(buffer);
        buffer.release();
    }
}
