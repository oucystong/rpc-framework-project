package com.ouc.rpc.framework.model;

import com.ouc.rpc.framework.remote.transport.RpcServer;
import com.ouc.rpc.framework.util.ApplicationContextUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
public class ServerModel implements InitializingBean {

    /**
     * @Description: 服务器ID
     */
    private String serverId;

    /**
     * @Description: 服务器IP地址
     */
    private String serverIp;

    /**
     * @Description: 服务器端口地址
     */
    private String serverPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 填充IP信息
        postProcess();
        // 开启一个专用来负责RPC调用的线程
        new RpcServer(serverPort).start();
    }

    /**
     * @Description: 后置处理器-填充一些需要RPC框架获得的Bean信息
     */
    private void postProcess() throws UnknownHostException {
        // 填充IP信息
        ServerModel serverModel = ApplicationContextUtil.getApplicationContext().getBean(ServerModel.class);
        serverModel.setServerIp(InetAddress.getLocalHost().getHostAddress());
    }
}
