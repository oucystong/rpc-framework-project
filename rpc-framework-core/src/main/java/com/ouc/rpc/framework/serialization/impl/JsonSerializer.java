//package com.ouc.rpc.framework.serialization.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.ouc.rpc.framework.serialization.Serializer;
//import lombok.extern.slf4j.Slf4j;
//
//
///**
// * @Description: Json序列化和反序列化方法实现
// * @Author: Mr.Tong
// */
//@Slf4j
//public class JsonSerializer implements Serializer {
//
//
//    @Override
//    public <T> byte[] serialize(T obj) {
//        String json = JSON.toJSONString(obj);
//        byte[] bytes = json.getBytes();
//        log.info("json serialize successfully");
//        return bytes;
//    }
//
//    @Override
//    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
//        String json = new String(bytes);
//        T t = JSON.parseObject(json, clazz);
//        log.info("json deserialize successfully");
//        return t;
//    }
//}
