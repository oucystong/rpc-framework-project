package com.ouc.rpc.framework.alignment;

import com.ouc.rpc.framework.remote.RpcRequestMessage;
import com.ouc.rpc.framework.remote.RpcResponseMessage;
import com.ouc.rpc.framework.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import org.apache.dubbo.common.extension.SPI;

import java.util.List;
import java.util.Map;

/**
 * @Description: 网络协议对齐接口 | 默认是sRPC网络协议和Dubbo网络协议的对齐
 * @Author: Mr.Tong
 */
@SPI("dubbo") // 此处指的是Dubbo协议
public interface NetProtocolAlignment {


    /**
     * @Description: 网络通信协议的对齐 | 编码协议对齐 | 将srpc网络通信协议对齐到其他RPC框架的网络通信协议
     */
    byte[] encodeProtocolAlignment(RpcRequestMessage requestMessage, int idOfSerializationType, Serializer serialzer);

    /**
     * @Description: 网络通信协议的对齐 | 解码协议对齐 | 将其他RPC框架的网络通信协议对齐到srpc框架的网络通信协议
     */
    RpcResponseMessage decodeProtocolAlignment(byte[] header, ByteBuf in);

    Integer getOriginIdOfSerializationType(String serializationType);


    Integer getTargetIdOfSerializationType(String serializationType);

    List<List<Object>> serializationMappingAlignment();


}
