package com.ouc.rpc.framework.benchmark.performance;

/**
 * @Description: 固定长度字符串生成器
 * @Author: Mr.Tong
 */
public class FixedLengthStrGenerator {


    public static String getFixedLengthStr(Integer length) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.toString().getBytes().length < length) {
            stringBuilder.append("a"); // 可以根据需要更改字符
        }
        return stringBuilder.toString().substring(0, length);
    }

}
