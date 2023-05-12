package com.ouc.rpc.framework.benchmark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Tong
 */
@Data
@AllArgsConstructor
public class CommonObject implements Serializable {
    private String stringParam;
    private Integer integerParam;
    private Long longParam;
    private Float floatParam;
    private Double doubleParam;
    private ArrayList<Object> stringListParam;
    private Map<String, String> stringStringMapParam;
}
