package com.ouc.rpc.framework.remote.transport.codec;

import com.ouc.rpc.framework.alignment.NetProtocolAlignment;
import com.ouc.rpc.framework.remote.Message;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.remote.enums.SerializerEnum;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.util.BytesUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @Description: 通用协议解码的过程
 * @Author: Mr.Tong
 */
public class Decode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 读取头部信息
        byte[] header = new byte[16];
        in.readBytes(header);

        Message message = null;
        // 73727063是srpc字符串的16进制 | sRPC框架的协议解码过程
        if (String.format("%04x", BytesUtil.bytes2int(header, 0)).equals("73727063")) {

            int serializerType = header[5];
            int dataLength = BytesUtil.bytes2int(header, 12);

            // 序列化消息
            ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
            Serializer serializer = serializerExtensionLoader.getExtension(SerializerEnum.typeOfId(serializerType));

            // 将字节数组内容取出
            byte[] bytes = new byte[dataLength];
            in.readBytes(bytes, 0, dataLength);

            // 反序列化
            message = serializer.deserialize(bytes, Message.class);
        } else {
            ExtensionLoader<NetProtocolAlignment> netProtocolAlignmentExtensionLoader = ExtensionLoader.getExtensionLoader(NetProtocolAlignment.class);
            NetProtocolAlignment netProtocolAlignment = netProtocolAlignmentExtensionLoader.getExtension("dubbo");
            message = netProtocolAlignment.decodeProtocolAlignment(header, in);
        }
        out.add(message);
    }
}
