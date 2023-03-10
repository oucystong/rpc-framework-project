package com.ouc.rpc.framework.util;

import com.ouc.rpc.framework.model.ReferenceServiceModel;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;

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
