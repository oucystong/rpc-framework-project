//package com.ouc.rpc.framework.serialization.impl;
//
//import com.caucho.hessian.io.HessianInput;
//import com.caucho.hessian.io.HessianOutput;
//import com.ouc.rpc.framework.serialization.Serializer;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
///**
// * @Description: Hessian序列化和反序列化实现
// * @Author: Mr.Tong
// */
//@Slf4j
//public class HessianSerializer implements Serializer {
//
//    @Override
//    public <T> byte[] serialize(T obj) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        HessianOutput ho = new HessianOutput(baos);
//        try {
//            ho.writeObject(obj);
//            log.info("hessian serialize successfully");
//        } catch (IOException e) {
//            log.error("hessian serialize has exception: {}", e.getMessage());
//        } finally {
//            try {
//                ho.close();
//                baos.close();
//            } catch (IOException e) {
//                log.error("close stream has exception: {}", e.getMessage());
//            }
//        }
//        return baos.toByteArray();
//    }
//
//    @Override
//    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        HessianInput hi = new HessianInput(bais);
//        Object o = null;
//        try {
//            o = hi.readObject();
//            log.info("hessian deserialize successfully");
//        } catch (IOException e) {
//            log.error("hessian deserialize has exception: {}", e.getMessage());
//        } finally {
//            try {
//                bais.close();
//                hi.close();
//            } catch (IOException e) {
//                log.error("close stream has exception: {}", e.getMessage());
//            }
//        }
//        return clazz.cast(o);
//    }
//}
