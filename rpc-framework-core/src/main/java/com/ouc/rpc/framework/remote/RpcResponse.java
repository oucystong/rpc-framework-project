package com.ouc.rpc.framework.remote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: RPC响应模型
 * @Author: Mr.Tong
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    /**
     * @Description: RPC请求ID
     */
    private String requestId;
    /**
     * @Description: RPC请求失败/成功标记
     */
    private Boolean isSuccess;
    /**
     * @Description: RPC响应结果
     */
    private Object result;
}
