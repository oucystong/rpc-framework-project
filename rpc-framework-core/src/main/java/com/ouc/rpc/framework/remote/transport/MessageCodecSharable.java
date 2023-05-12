//package com.ouc.rpc.framework.remote.transport;
//
//import com.ouc.rpc.framework.model.ReferenceServiceModel;
//import com.ouc.rpc.framework.model.SerializerModel;
//import com.ouc.rpc.framework.remote.Message;
//import com.ouc.rpc.framework.remote.enums.SerializerEnum;
//import com.ouc.rpc.framework.serialization.Serializer;
//import com.ouc.rpc.framework.serialization.SerializerFactory;
//import com.ouc.rpc.framework.util.ApplicationContextUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageCodec;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.common.extension.ExtensionLoader;
//import org.apache.dubbo.common.io.Bytes;
//
//import java.util.List;
//
///**
// * @Description: 消息编解码器
// * @Author: Mr.Tong
// */
//@Slf4j
//@ChannelHandler.Sharable
//public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
//
////    private ReferenceServiceModel referenceServiceModel;
////
////    public MessageCodecSharable(ReferenceServiceModel referenceServiceModel) {
////        this.referenceServiceModel = referenceServiceModel;
////    }
//
//    /**
//     * @Description: 协议消息编码器
//     */
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
//
////        ByteBuf buffer = ctx.alloc().buffer();
////        // 4个字节的魔数
////        buffer.writeBytes(new byte[]{'s', 'r', 'p', 'c'});
////        // 1个字节的版本号
////        buffer.writeByte(1);
////        // 1个字节的序列化方式
////        if (ApplicationContextUtil.containsBean("serializerModel")) {// 存在用户配置
////            SerializerModel serializerModel = ApplicationContextUtil.getApplicationContext().getBean("serializerModel", SerializerModel.class);
////            buffer.writeByte(SerializerEnum.idOfType(serializerModel.getSerializerType()));
////        } else {// 使用默认的动态代理方式
////            buffer.writeByte(SerializerEnum.idOfType(ExtensionLoader.getExtensionLoader(Serializer.class).getDefaultExtensionName()));
////        }
////        // 2个字节的消息类型
////        buffer.writeByte(msg.getMessageType());
////        buffer.writeByte(0xff);
////        // 4个字节的RPC请求序列号
////        buffer.writeInt(123456);
////        // 序列化消息
////        byte[] serialize = SerializerFactory.SERIALIZER_SERVICE.serialize(msg);
////        // 4个字节有效载荷数据的长度
////        buffer.writeInt(serialize.length);
////        // 写入Buffer
////        buffer.writeBytes(serialize);
////        out.add(buffer);
//
//        // 编码为Dubbo协议数据包
////        ByteBuf buffer = ctx.alloc().buffer();
////        byte[] header = new byte[16];
////        // 2个字节的魔数
////        Bytes.short2bytes((short) 0xdabb, header);
////        // 1个字节标记请求和序列化 |
////        header[2] = (byte) ((byte) 0x80 | (byte) 2);
////        header[2] |= (byte) 0x40;
//
//
//    }
//
//
//    /**
//     * @Description: 协议消息解码器
//     */
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
////        // 读取魔数
////        String magicNumber = msg.readBytes(4).toString();
////        // 读取版本号
////        byte version = msg.readByte();
////        // 读取序列化方式
////        int serializerType = ((int) msg.readByte());
////        // 读取消息类型
////        byte msgType1 = msg.readByte();
////        byte msgType2 = msg.readByte();
////        // 读取请求的ID值
////        int sequenceId = msg.readInt();
////        // 读取有效载荷数据的长度
////        int dataLength = msg.readInt();
////
////        // 序列化消息
////        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
////        Serializer serializer = serializerExtensionLoader.getExtension(SerializerEnum.typeOfId(serializerType));
////
////        // 将字节数组内容取出
////        byte[] bytes = new byte[dataLength];
////        msg.readBytes(bytes, 0, dataLength);
////
////        // 反序列化
////        Message deserialize = serializer.deserialize(bytes, Message.class);
////
////        out.add(deserialize);
//    }
//
//
//}
