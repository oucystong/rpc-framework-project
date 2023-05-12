//package com.ouc.rpc.framework.serialization;
//
//import com.ouc.rpc.framework.model.ProxyModel;
//import com.ouc.rpc.framework.model.SerializerModel;
//import com.ouc.rpc.framework.proxy.ProxyService;
//import com.ouc.rpc.framework.util.ApplicationContextUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.common.extension.ExtensionLoader;
//
///**
// * @Description: 序列化服务的工厂类
// * @Author: Mr.Tong
// */
//@Slf4j
//public class SerializerFactory {
//
//
//    // 在类加载的时候确定动态代理方式
//    public static Serializer SERIALIZER_SERVICE = null;
//
//    // 在静态变量之后执行
//    static {
//        ExtensionLoader<Serializer> extensionLoader = ExtensionLoader.getExtensionLoader(Serializer.class);
//        // 判断用户是否配置 | 若配置则使用用户配置的相关内容 | 否则使用默认的动态代理方式
//        if (ApplicationContextUtil.containsBean("serializerModel")) {// 存在用户配置
//            SerializerModel serializerModel = ApplicationContextUtil.getApplicationContext().getBean("serializerModel", SerializerModel.class);
//            SERIALIZER_SERVICE = extensionLoader.getExtension(serializerModel.getSerializerType());
//            log.info("use {} serializer", serializerModel.getSerializerType());
//        } else {// 使用默认的动态代理方式
//            SERIALIZER_SERVICE = extensionLoader.getDefaultExtension();
//            log.info("use default serializer");
//        }
//    }
//}
