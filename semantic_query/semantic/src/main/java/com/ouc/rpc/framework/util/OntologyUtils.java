package com.ouc.rpc.framework.util;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Property;

/**
 * @Description: 处理本体工具类
 * @Author: Mr.Tong
 */
public class OntologyUtils {

    /**
     * @Description: 获取本体中概念的简单名称
     */
    public static String getConceptSimpleName(OntClass ontClass) {
//        String classSimpleName = ontClass.getModel().getGraph().getPrefixMapping().shortForm(ontClass.getURI());
//        return classSimpleName.substring(1);
        return ontClass.getLocalName();
    }

    /**
     * @Description: 获取本体中概念属性的简单名称
     */
    public static String getPropertySimpleName(Property ontProperty) {
        return ontProperty.getLocalName();
    }


}
