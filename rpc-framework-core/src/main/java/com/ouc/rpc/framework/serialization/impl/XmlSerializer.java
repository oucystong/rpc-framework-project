package com.ouc.rpc.framework.serialization.impl;

import com.ouc.rpc.framework.serialization.Serializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;

/**
 * @Description: XML序列化和反序列化实现
 * @Author: Mr.Tong
 */
public class XmlSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(obj);
        return xml.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);// 设置权限
        String xml = new String(bytes);
        Object obj = xstream.fromXML(xml);
        return clazz.cast(obj);
    }
}
