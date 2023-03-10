package com.ouc.rpc.framework.util;

import org.springframework.context.ApplicationContext;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public final class ApplicationContextUtil {

    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static Boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

}
