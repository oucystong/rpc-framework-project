//package com.ouc.rpc.framework.consumer.config;
//
//import com.ouc.rpc.framework.model.ReferenceServiceModel;
//import com.ouc.rpc.framework.model.ApplicationModel;
//import com.ouc.rpc.framework.model.ClientModel;
//import com.ouc.rpc.framework.model.ZooKeeperModel;
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
//    @Value("${rpc.zk.address}")
//    private String zkAddress;
//
//    @Value("${rpc.service.consumer.id}")
//    private String serviceId;
//
//    @Value("${rpc.service.consumer.name}")
//    private String serviceName;
//
//    @Value("${rpc.service.consumer.impl}")
//    private String serviceImpl;
//
//
//    @Bean
//    public ApplicationModel rpcApplicationModel() {
//        ApplicationModel applicationModel = new ApplicationModel();
//        applicationModel.setApplicationName(applicationName);
//        return applicationModel;
//    }
//
//    @Bean
//    public ZooKeeperModel zooKeeperAddressModel() {
//        ZooKeeperModel zooKeeperModel = new ZooKeeperModel();
//        zooKeeperModel.setZooKeeperAddress(zkAddress);
//        return zooKeeperModel;
//    }
//
//    @Bean
//    public ClientModel rpcClientModel() {
//        ClientModel clientModel = new ClientModel();
//        return clientModel;
//    }
//
//    @Bean
//    public ReferenceServiceModel referenceServiceModel() throws UnknownHostException {
//        ReferenceServiceModel referenceServiceModel = new ReferenceServiceModel();
//        referenceServiceModel.setReferenceServiceId(serviceId);
//        referenceServiceModel.setReferenceServiceName(serviceName);
//        referenceServiceModel.setReferenceServiceImpl(serviceImpl);
//        referenceServiceModel.setConsumerInstanceIp(InetAddress.getLocalHost().getHostAddress());
//        return referenceServiceModel;
//    }
//
//
//}
