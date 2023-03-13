package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.remote.transport.RpcServer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Description: RPC服务器模型
 * @Author: Mr.Tong
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RpcServerModel implements InitializingBean {

    private String rpcServerId;

    private String port;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 开启一个专用来负责RPC调用的线程
        new RpcServer(port).start();
    }
}
