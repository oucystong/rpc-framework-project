package com.ouc.rpc.framework.remote.enums;

/**
 * @Description: 序列化方式字符串和数字之间的对应关系 | 维护映射关系
 * @Author: Mr.Tong
 */
public enum SerializerEnum {

    JSON(1, "json"),

    XML(2, "xml"),

    JDK(3, "jdk"),

    KRYO(4, "kryo"),

    HESSIAN(5, "hessian"),

    FST(6, "fst"),

    PROTOSTUFF(7, "protostuff"),

    MULTIPLE(810, "multiple"),

    HESSIAN2(8, "hessian2");

    private final Integer id;
    private final String type;


    SerializerEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }


    public static Integer idOfType(String type) {
        // 遍历获取对应的ID值
        for (int i = 0; i < SerializerEnum.values().length; i++) {
            if (SerializerEnum.values()[i].getType().equals(type)) {
                return SerializerEnum.values()[i].getId();
            }
        }
        // 找不到对应的序列化方式
        return -1;
    }

    public static String typeOfId(Integer id) {
        // 遍历获取对应的ID值
        for (int i = 0; i < SerializerEnum.values().length; i++) {
            if (SerializerEnum.values()[i].getId().equals(id)) {
                return SerializerEnum.values()[i].getType();
            }
        }
        // 找不到对应的序列化方式
        return "";
    }
}
