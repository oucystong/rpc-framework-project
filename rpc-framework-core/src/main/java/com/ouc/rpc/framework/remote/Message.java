package com.ouc.rpc.framework.remote;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Data
public abstract class Message implements Serializable {


    // 获取特定消息的Class类对象
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    // 存储每个消息的Class类对象
    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    // 消息ID
    private Long sequenceId;

    // 当前消息通过何种通信协议发送
    private String netProtocol;

    // 当前消息的序列化方式
    private String serializationType;

    // 消息类型
    private Integer messageType;

    public abstract int getMessageType();

    public static final int RPC_REQUEST_MESSAGE_TYPE = 1;

    public static final int RPC_RESPONSE_MESSAGE_TYPE = 2;


    static {
        messageClasses.put(RPC_REQUEST_MESSAGE_TYPE, RpcRequestMessage.class);
        messageClasses.put(RPC_RESPONSE_MESSAGE_TYPE, RpcResponseMessage.class);
    }


}
