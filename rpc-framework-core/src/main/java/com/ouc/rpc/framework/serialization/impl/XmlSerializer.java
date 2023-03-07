package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description: XML序列化和反序列化实现
 * @Author: Mr.Tong
 */
@Slf4j
public class XmlSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(obj);
        byte[] result = xml.getBytes();
        log.info("xml serialize successfully");
        return result;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);// 设置权限
        String xml = new String(bytes);
        Object obj = xstream.fromXML(xml);
        T t = clazz.cast(obj);
        log.info("xml deserialize successfully");
        return t;
    }
}
