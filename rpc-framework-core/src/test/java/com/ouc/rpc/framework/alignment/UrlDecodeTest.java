package com.ouc.rpc.framework.alignment;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class UrlDecodeTest {

    @Test
    void testDecode() throws UnsupportedEncodingException {
        String str = "dubbo%3A%2F%2F10.155.222.211%3A20890%2Fcom.ouc.rpc.framework.api.service.GreetingService%3Fanyhost%3Dtrue%26application%3Dservice_provider_dubbo%26bean.name%3Dcom.ouc.rpc.framework.api.service.GreetingService%26default.deprecated%3Dfalse%26default.dynamic%3Dfalse%26default.register%3Dtrue%26deprecated%3Dfalse%26dubbo%3D2.0.2%26dynamic%3Dfalse%26generic%3Dfalse%26interface%3Dcom.ouc.rpc.framework.api.service.GreetingService%26methods%3DconsumerTypeServiceCustom%2CfunctionTypeServiceCustom%2CfunctionTypeServiceBase%2CsupplierTypeServiceBase%2CsupplierTypeServiceCustom%2CconsumerTypeServiceBase%26pid%3D51325%26register%3Dtrue%26release%3D2.7.1%26side%3Dprovider%26timestamp%3D1682149361431";
        String utf8 = URLDecoder.decode(str, "UTF-8");
        String serviceName = utf8.substring(utf8.lastIndexOf("/") + 1, utf8.indexOf("?"));
        String serviceSimpleName = serviceName.substring(serviceName.lastIndexOf(".") + 1);
        String serviceId = new StringBuilder().append(Character.toLowerCase(serviceSimpleName.charAt(0))).append(serviceSimpleName.substring(1)).toString();
        String serviceImpl = serviceSimpleName + "DubboImpl";
        String ipAndPort = utf8.substring(utf8.indexOf("//") + 1, utf8.lastIndexOf("/"));
        String ip = ipAndPort.substring(1, ipAndPort.indexOf(":"));
        String port = ipAndPort.substring(ipAndPort.indexOf(":") + 1);
        String serializationTmp = utf8.substring(utf8.indexOf("serialization"));
        String serialization = serializationTmp.substring(serializationTmp.indexOf("=")+1,serializationTmp.indexOf("&"));

        System.out.println(serviceName);
        System.out.println(serviceId);
        System.out.println(serviceImpl);
//        System.out.println(ipAndPort);
        System.out.println(ip);
        System.out.println(port);
        System.out.println(serialization);



    }
}
