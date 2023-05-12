package com.ouc.rpc.framework.alignment;

import com.ouc.rpc.framework.model.ExposeServiceModel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description: Dubbo服务获取及解析
 * @Author: Mr.Tong
 */
public class DubboServiceAccess implements ServiceAccess {

    @Override
    public String buildServicePath(String serviceName) {
        return "/dubbo/" + serviceName + "/providers";
    }

    @Override
    public ExposeServiceModel parseChildNodeOfServicePath(String childNode) {
        // 将Dubbo的叶子节点解码
        String decodeResult = null;
        try {
            decodeResult = URLDecoder.decode(childNode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        // 解析过程
        String serviceName = decodeResult.substring(decodeResult.lastIndexOf("/") + 1, decodeResult.indexOf("?"));
        String serviceSimpleName = serviceName.substring(serviceName.lastIndexOf(".") + 1);
        String serviceId = new StringBuilder().append(Character.toLowerCase(serviceSimpleName.charAt(0))).append(serviceSimpleName.substring(1)).toString();
        String serviceImpl = serviceSimpleName + "DubboImpl";
        String ipAndPort = decodeResult.substring(decodeResult.indexOf("//") + 1, decodeResult.lastIndexOf("/"));
        String ip = ipAndPort.substring(1, ipAndPort.indexOf(":"));
        String port = ipAndPort.substring(ipAndPort.indexOf(":") + 1);
        String serializationTmp = decodeResult.substring(decodeResult.indexOf("serialization"));
        String serialization = serializationTmp.substring(serializationTmp.indexOf("=") + 1, serializationTmp.indexOf("&"));

        // 根据解码的结果构建ExposeServiceModel
        ExposeServiceModel exposeServiceModel = new ExposeServiceModel();
        exposeServiceModel.setExposeServiceId(serviceId);
        exposeServiceModel.setExposeServiceName(serviceName);
        exposeServiceModel.setExposeServiceImpl(serviceImpl);
        exposeServiceModel.setProviderInstanceIp(ip);
        exposeServiceModel.setProviderInstancePort(port);
        if (decodeResult.indexOf("serialization") == -1) {
            exposeServiceModel.setSerializerType("hessian2");
        }
        exposeServiceModel.setSerializerType(serialization);
        // 使用默认的Dubbo协议
        exposeServiceModel.setNetworkProtocolType("dubbo");

        return exposeServiceModel;
    }
}
