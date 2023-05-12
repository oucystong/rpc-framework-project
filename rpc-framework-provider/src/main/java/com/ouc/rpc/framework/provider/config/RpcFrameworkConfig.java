//package com.ouc.rpc.framework.provider.config;
//
//import com.ouc.rpc.framework.model.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///**
// * @Description:
// * @Author: Mr.Tong
// */
//@Configuration
//@PropertySource(value = "classpath:rpc.properties")
//public class RpcFrameworkConfig {
//
//    @Value("${rpc.application.name}")
//    private String applicationName;
//
//    @Bean
//    public ApplicationModel rpcApplicationModel() {
//        ApplicationModel applicationModel = new ApplicationModel();
//        applicationModel.setApplicationName(applicationName);
//        return applicationModel;
//    }
//
//    @Value("${rpc.port}")
//    private String rpcPort;
//
//    @Value("${rpc.zk.address}")
//    private String zkAddress;
//
//    @Value("${rpc.service.provider.id}")
//    private String serviceId;
//
//    @Value("${rpc.service.provider.name}")
//    private String serviceName;
//
//    @Value("${rpc.service.provider.impl}")
//    private String serviceImpl;
//
//
//
//
//    @Bean
//    public ZooKeeperModel zooKeeperAddressModel() {
//        ZooKeeperModel zooKeeperModel = new ZooKeeperModel();
//        zooKeeperModel.setZooKeeperAddress(zkAddress);
//        return zooKeeperModel;
//    }
//
//
//    @Bean
//    public ServerModel rpcServerModel() {
//        ServerModel serverModel = new ServerModel();
//        serverModel.setServerPort(rpcPort);
//        return serverModel;
//    }
//
//
//    @Bean
//    public ExposeServiceModel exposeServiceModel() throws UnknownHostException {
//        ExposeServiceModel exposeServiceModel = new ExposeServiceModel();
//        exposeServiceModel.setExposeServiceId(serviceId);
//        exposeServiceModel.setExposeServiceName(serviceName);
//        exposeServiceModel.setExposeServiceImpl(serviceImpl);
//        exposeServiceModel.setProviderInstanceIp(InetAddress.getLocalHost().getHostAddress());
//        exposeServiceModel.setProviderInstancePort(rpcPort);
//        return exposeServiceModel;
//    }
//
//}
