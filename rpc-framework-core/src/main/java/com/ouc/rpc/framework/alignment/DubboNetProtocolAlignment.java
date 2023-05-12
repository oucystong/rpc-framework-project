package com.ouc.rpc.framework.alignment;

import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.serialization.Serializer;
import com.ouc.rpc.framework.serialization.impl.Hessian2Serializer;
import com.ouc.rpc.framework.util.BytesUtil;
import io.netty.buffer.ByteBuf;
import org.apache.dubbo.common.utils.ReflectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: sRPC网络协议和Dubbo网络协议的对齐
 * @Author: Mr.Tong
 */
public class DubboNetProtocolAlignment implements NetProtocolAlignment {


    @Override
    public byte[] encodeProtocolAlignment(RpcRequestMessage requestMessage, int idOfSerializationType, Serializer serialzer) {

        // Dubbo协议数据包的头填充
        byte[] header = new byte[16];
        // 2个字节的魔数
        BytesUtil.short2bytes((short) 0xdabb, header);
        // 1个字节标记请求和序列化
        header[2] = (byte) ((byte) 0x80 | (byte) idOfSerializationType);
        header[2] |= (byte) 0x40;

        BytesUtil.long2bytes(requestMessage.getSequenceId(), header, 4);

        // 将srpc数据对齐到dubbo的有效载荷数据

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // 序列化
            serialzer.writeUTF("2.0.2");
            serialzer.writeUTF(requestMessage.getInterfaceName());
            serialzer.writeUTF("0.0.0");
            serialzer.writeUTF(requestMessage.getMethodName());
            serialzer.writeUTF(ReflectUtils.getDesc(requestMessage.getArgTypes()));
            Object[] args = requestMessage.getArgs();
            for (Object arg : args) {
                serialzer.writeObject(arg);
            }
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("path", requestMessage.getInterfaceName());
            stringStringHashMap.put("interface", requestMessage.getInterfaceName());
            stringStringHashMap.put("version", "0.0.0");
            serialzer.writeObject(stringStringHashMap);
            serialzer.flushBuffer();
            // 获取序列化的数据
            byteArrayOutputStream = serialzer.getOutputStream();
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BytesUtil.int2bytes(byteArrayOutputStream.toByteArray().length, header, 12);
        // 字节数组合并返回结果
        byte[] result = new byte[header.length + byteArrayOutputStream.toByteArray().length];
        System.arraycopy(header, 0, result, 0, header.length);
        System.arraycopy(byteArrayOutputStream.toByteArray(), 0, result, header.length, byteArrayOutputStream.toByteArray().length);

        return result;
    }

    @Override
    public RpcResponseMessage decodeProtocolAlignment(byte[] header, ByteBuf in) {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        // 序列化方式
        int serializationType = header[2] & 0x1f;
        // 响应状态
        byte status = header[3];
        // 响应哪一个请求
        long requestId = BytesUtil.bytes2long(header, 4);
        // 数据长度
        int len = BytesUtil.bytes2int(header, 12);

        byte[] body = new byte[len];

        in.readBytes(body);

        Serializer serializer = new Hessian2Serializer();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        serializer.setIntputStream(byteArrayInputStream);

        Object result = null;
        // 响应标识
        try {
            byte flag = serializer.readByte();
            result = serializer.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        rpcResponseMessage.setIsSuccess(true);
        rpcResponseMessage.setResult(result);
        rpcResponseMessage.setSequenceId(requestId);

        return rpcResponseMessage;
    }

    @Override
    public Integer getOriginIdOfSerializationType(String serializationType) {
        HashMap<String, Integer> serialTypeAndId = new HashMap<>();
        serialTypeAndId.put("hessian2", 2);
        serialTypeAndId.put("java", 3);
        serialTypeAndId.put("compactedjava", 4);
        serialTypeAndId.put("fastjson", 6);
        serialTypeAndId.put("nativejava", 7);
        serialTypeAndId.put("kryo", 8);
        serialTypeAndId.put("fst", 9);
        serialTypeAndId.put("protostuff", 10);
        return serialTypeAndId.get(serializationType);
    }

    @Override
    public Integer getTargetIdOfSerializationType(String serializationType) {
        if (serializationType.equals("hessian2")) {
            return 9;
        }
        return -1;
    }

    @Override
    public List<List<Object>> serializationMappingAlignment() {
        return null;
    }

}
