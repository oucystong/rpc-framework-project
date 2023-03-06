package com.ouc.rpc.framework.remote;

import lombok.*;

import java.io.Serializable;

/**
 * @Description: RPC请求模型
 * @Author: Mr.Tong
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RpcRequest implements Serializable {

    /**
     * @Description: RPC请求ID
     */
    private String requestId;

    /**
     * @Description: 请求的接口名称
     */
    private String interfaceName;

    /**
     * @Description: 请求的方法名称
     */
    private String methodName;

    /**
     * @Description: 请求的参数类型
     */
    private String[] types;

    /**
     * @Description: 请求的参数值
     */
    private Object[] args;


}
