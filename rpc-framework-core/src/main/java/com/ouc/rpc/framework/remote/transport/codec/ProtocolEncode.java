//package com.ouc.rpc.framework.remote.transport.codec;
//
//import com.ouc.rpc.framework.model.SerializerModel;
//import com.ouc.rpc.framework.remote.Message;
//import com.ouc.rpc.framework.remote.RpcRequestMessage;
//import com.ouc.rpc.framework.remote.RpcResponseMessage;
//import com.ouc.rpc.framework.remote.enums.SerializerEnum;
//import com.ouc.rpc.framework.serialization.Serializer;
//import com.ouc.rpc.framework.util.ApplicationContextUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//import org.apache.dubbo.common.extension.ExtensionLoader;
//import com.ouc.rpc.framework.serialization.SerializerFactory;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;
//import org.apache.dubbo.common.serialize.hessian2.Hessian2Serialization;
//import org.apache.dubbo.common.utils.ReflectUtils;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//
///**
// * @Description: 协议编码
// * @Author: Mr.Tong
// */
//public class ProtocolEncode extends MessageToByteEncoder {
//
//
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
////        // 判断消息类型 | 响应消息和请求消息具有不同的编码过程
////        if (msg instanceof RpcRequestMessage) { // 请求消息
////            encodeRequest(ctx, (RpcRequestMessage) msg, out);
////        } else if (msg instanceof RpcResponseMessage) { // 响应消息
////            encodeResponse(ctx, (RpcResponseMessage) msg, out);
////        }
//
////        if(msg instanceof Message){
////
////
////        }
//
//
//
//
//    }
//
//    // 编码响应消息
//    private void encodeResponse(ChannelHandlerContext ctx, RpcResponseMessage msg, ByteBuf out) {
//
//        // 4个字节的魔数
//        out.writeBytes(new byte[]{'s', 'r', 'p', 'c'});
//        // 1个字节的版本号
//        out.writeByte(1);
//        // 1个字节的序列化方式
//        if (ApplicationContextUtil.containsBean("serializerModel")) {// 存在用户配置
//            SerializerModel serializerModel = ApplicationContextUtil.getApplicationContext().getBean("serializerModel", SerializerModel.class);
//            out.writeByte(SerializerEnum.idOfType(serializerModel.getSerializerType()));
//        } else {// 使用默认的动态代理方式
//            out.writeByte(SerializerEnum.idOfType(ExtensionLoader.getExtensionLoader(Serializer.class).getDefaultExtensionName()));
//        }
//        // 2个字节的消息类型
//        out.writeByte(msg.getMessageType());
//        out.writeByte(0xff);
//        // 4个字节的RPC请求序列号
//        out.writeInt(msg.getSequenceId().intValue());
//        // 序列化消息
//        byte[] serialize = SerializerFactory.SERIALIZER_SERVICE.serialize(msg);
//        // 4个字节有效载荷数据的长度
//        out.writeInt(serialize.length);
//        // 写入有效载荷数据
//        out.writeBytes(serialize);
//
//    }
//
//    // 编码请求消息
//    private void encodeRequest(ChannelHandlerContext ctx, RpcRequestMessage msg, ByteBuf out) throws IOException {
//
////        // 4个字节的魔数
////        out.writeBytes(new byte[]{'s', 'r', 'p', 'c'});
////        // 1个字节的版本号
////        out.writeByte(1);
////        // 1个字节的序列化方式
////        if (ApplicationContextUtil.containsBean("serializerModel")) {// 存在用户配置
////            SerializerModel serializerModel = ApplicationContextUtil.getApplicationContext().getBean("serializerModel", SerializerModel.class);
////            out.writeByte(SerializerEnum.idOfType(serializerModel.getSerializerType()));
////        } else {// 使用默认的动态代理方式
////            out.writeByte(SerializerEnum.idOfType(ExtensionLoader.getExtensionLoader(Serializer.class).getDefaultExtensionName()));
////        }
////        // 2个字节的消息类型
////        out.writeByte(msg.getMessageType());
////        out.writeByte(0xff);
////        // 4个字节的RPC请求序列号
////        out.writeInt(msg.getSequenceId().intValue());
////        // 序列化消息
////        byte[] serialize = SerializerFactory.SERIALIZER_SERVICE.serialize(msg);
////        // 4个字节有效载荷数据的长度
////        out.writeInt(serialize.length);
////        // 写入有效载荷数据
////        out.writeBytes(serialize);
//
//
//
//
//
//
////        // 编码为Dubbo协议数据包
////        // 头
////        ByteBuf buffer = ctx.alloc().buffer();
////        byte[] header = new byte[16];
////        // 2个字节的魔数
////        Bytes.short2bytes((short) 0xdabb, header);
////        // 1个字节标记请求和序列化 |
////        header[2] = (byte) ((byte) 0x80 | (byte) 2);
////        header[2] |= (byte) 0x40;
////
////        Bytes.long2bytes(msg.getSequenceId(), header, 4);
////
////        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////
////        // 有效载荷数据
////        Hessian2ObjectOutput hessian2ObjectOutput = new Hessian2ObjectOutput(byteArrayOutputStream);
////        hessian2ObjectOutput.writeUTF("2.0.2");
////        hessian2ObjectOutput.writeUTF(msg.getInterfaceName());
////        hessian2ObjectOutput.writeUTF("0.0.0");
////        hessian2ObjectOutput.writeUTF(msg.getMethodName());
////        hessian2ObjectOutput.writeUTF("Ljava/lang/String;");
////        hessian2ObjectOutput.writeUTF("xiaotong");
////        HashMap<String, String> stringStringHashMap = new HashMap<>();
////        stringStringHashMap.put("path", msg.getInterfaceName());
////        stringStringHashMap.put("interface", msg.getInterfaceName());
////        stringStringHashMap.put("version", "0.0.0");
////        hessian2ObjectOutput.writeObject(stringStringHashMap);
////        hessian2ObjectOutput.flushBuffer();
////
////        byteArrayOutputStream.flush();
////
////        Bytes.int2bytes(byteArrayOutputStream.toByteArray().length, header, 12);
////        // 写入协议头
////        buffer.writeBytes(header);
//////        buffer.writeInt(byteArrayOutputStream.toByteArray().length);
////
////        // 写入协议体
////        buffer.writeBytes(byteArrayOutputStream.toByteArray());
////
////        out.writeBytes(buffer);
//
//
//    }
//}
