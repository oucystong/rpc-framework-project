package com.ouc.rpc.framework.util;

import com.ouc.rpc.framework.model.ServerModel;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * @Description: RPC框架的常用方法工具类
 * @Author: Mr.Tong
 */
public class RpcCommonUtil {

    public static final String IP_PORT_REGEX = "^(((25[0-5]|2[0-4]d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))\\:([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";

    /**
     * @Description: 通过正则表达式判断当前IP和端口地址是否正确
     */
    public static boolean isCorrectIpPort(String ipAndPort) {
        return Pattern.matches(RpcCommonUtil.IP_PORT_REGEX, ipAndPort);
    }

    /**
     * @Description: 获取特定类型的BeanDefinition
     */
    public static RootBeanDefinition getBeanDefinition(Class modelClazz) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(modelClazz);
        rootBeanDefinition.setLazyInit(false);
        return rootBeanDefinition;
    }

}
