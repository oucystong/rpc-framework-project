//package com.ouc.rpc.framework.remote.transport.codec;
//
//import com.ouc.rpc.framework.remote.Message;
//import com.ouc.rpc.framework.remote.RpcRequestMessage;
//import com.ouc.rpc.framework.remote.RpcResponseMessage;
//import com.ouc.rpc.framework.remote.enums.SerializerEnum;
//import com.ouc.rpc.framework.serialization.Serializer;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import org.apache.dubbo.common.extension.ExtensionLoader;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.List;
//
///**
// * @Description: 协议解码
// * @Author: Mr.Tong
// */
//public class ProtocolDecode extends ByteToMessageDecoder {
//
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
////
////        // 读取魔数
////        String magicNumber = in.readBytes(4).toString();
////        // 读取版本号
////        byte version = in.readByte();
////        // 读取序列化方式
////        int serializerType = ((int) in.readByte());
////        // 读取消息类型
////        byte msgType1 = in.readByte();
////        byte msgType2 = in.readByte();
////        // 读取请求的ID值
////        int sequenceId = in.readInt();
////        // 读取有效载荷数据的长度
////        int dataLength = in.readInt();
////
////        // 序列化消息
////        ExtensionLoader<Serializer> serializerExtensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
////        Serializer serializer = serializerExtensionLoader.getExtension(SerializerEnum.typeOfId(serializerType));
////
////        // 将字节数组内容取出
////        byte[] bytes = new byte[dataLength];
////        in.readBytes(bytes, 0, dataLength);
////
////        // 反序列化
////        Message deserialize = serializer.deserialize(bytes, Message.class);
////
////        out.add(deserialize);
//
//
//
//
//
//
//        byte[] header = new byte[16];
//
//        in.readBytes(header);
//
//        // 数据长度
//        int len = Bytes.bytes2int(header, 12);
//
//        // 响应哪一个请求
//        long requestId = Bytes.bytes2long(header, 4);
//
//        // 响应状态
//        byte status = header[3];
//
//
//        byte[] body = new byte[len];
//        in.readBytes(body);
//
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
//
//        Hessian2ObjectInput hessian2ObjectInput = new Hessian2ObjectInput(byteArrayInputStream);
//
//
//        // 响应标识
//        byte flag = hessian2ObjectInput.readByte();
//
//        Object value = hessian2ObjectInput.readObject();
//
//
//
//
//        // 对拿到的数据进行反序列化
//
//        Object o = hessian2ObjectInput.readObject();
//
//        System.out.println("===");
//
//
//    }
//}
