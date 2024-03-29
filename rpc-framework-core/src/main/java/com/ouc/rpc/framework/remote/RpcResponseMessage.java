package com.ouc.rpc.framework.remote;

import lombok.*;

import java.io.Serializable;

/**
 * @Description: RPC响应模型
 * @Author: Mr.Tong
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class RpcResponseMessage extends Message implements Serializable {
    /**
     * @Description: RPC请求失败/成功标记
     */
    private Boolean isSuccess;
    /**
     * @Description: RPC响应结果
     */
    private Object result;

    /**
     * @Description: RPC响应结果
     */
    private Object error;

    @Override
    public int getMessageType() {
        return RPC_RESPONSE_MESSAGE_TYPE;
    }
}
