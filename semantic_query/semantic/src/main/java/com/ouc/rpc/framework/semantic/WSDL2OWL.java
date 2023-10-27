package com.ouc.rpc.framework.semantic;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 将WSDL转化为临时本体
 * @Author: Mr.Tong
 */
public class WSDL2OWL {

    /**
     * @Description: WSDL解析器
     */
    private WSDLParser wsdlParser;

    /**
     * @Description: 映射数据 | Key:tmpOntology Value:WSDLType
     */
    private HashMap<String, String> mapperData = new HashMap<>();


    public WSDL2OWL(WSDLParser wsdlParser) {
        this.wsdlParser = wsdlParser;
    }

    /**
     * @Description: 获取映射数据
     */
    public HashMap<String, String> getMapperData() {
        return mapperData;
    }

    /**
     * @Description: 根据WSDL文档产生临时本体文件
     */
    public OntModel getTmpOntologyFromWSDL() {

        // 创建一个新的本体模型
        OntModel model = ModelFactory.createOntologyModel();

        // 命名空间
        HashMap<String, String> serviceInfoName = wsdlParser.getServiceInfoName();
        String targetNameSpace = serviceInfoName.get(WSDLParser.SERVICE_TARGET_NAMESPACE);

        // 命名空间和前缀
        String ns = targetNameSpace + "#";
        String prefix = "";

        // 设置命名空间前缀
        model.setNsPrefix(prefix, ns);

        // 简单类型
        List<Element> allSimpleNodes = wsdlParser.getAllSimpleNodes();
        ArrayList<String> simpleTypeName = new ArrayList<>();
        HashMap<String, OntClass> simpleTypeClasses = new HashMap<>();

        // 创建本体类
        for (int i = 0; i < allSimpleNodes.size(); i++) {
            String wsdlType = wsdlParser.getWSDLType(allSimpleNodes.get(i).attribute(WSDLParser.ATTR_NAME).getValue());
            OntClass aClass = model.createClass(ns + preHandle(wsdlType));
            simpleTypeName.add(preHandle(wsdlType));
            simpleTypeClasses.put(preHandle(wsdlType), aClass);

            // 存储映射数据
            mapperData.put(preHandle(wsdlType), wsdlType);
        }

        // 复杂类型
        List<Element> allComplexNodes = wsdlParser.getAllComplexNodes();
        ArrayList<String> complexTypeName = new ArrayList<>();
        HashMap<String, OntClass> complexTypeClasses = new HashMap<>();

        // 创建本体类
        for (int i = 0; i < allComplexNodes.size(); i++) {
            String wsdlType = wsdlParser.getWSDLType(allComplexNodes.get(i).attribute(WSDLParser.ATTR_NAME).getValue());
            OntClass aClass = model.createClass(ns + preHandle(wsdlType));
            complexTypeName.add(preHandle(wsdlType));
            complexTypeClasses.put(preHandle(wsdlType), aClass);

            // 存储映射数据
            mapperData.put(preHandle(wsdlType), wsdlType);
        }

        // 复杂类型的关系和属性
        HashMap<String, List<Element>> complexTypeProperties = new HashMap<>();
        HashMap<String, String> complexTypeObjectProperties = new HashMap<>();
        HashMap<String, String> complexTypeDataProperties = new HashMap<>();


        for (int i = 0; i < allComplexNodes.size(); i++) {

            List<Element> elements = allComplexNodes.get(i).element(WSDLParser.TAG_SEQUENCE).elements(WSDLParser.TAG_ELEMENT);
            String domainName = complexTypeName.get(i);
            // 存储复杂类型的关系和属性
            complexTypeProperties.put(domainName, elements);

            // 遍历关系和属性
            for (Element element : elements) {
                String tmp = element.attribute(WSDLParser.ATTR_TYPE).getValue();
                String type = preHandle(tmp.contains(":") ? tmp.substring(tmp.indexOf(":") + 1) : tmp);
                String value = preHandle(element.attribute(WSDLParser.ATTR_NAME).getValue());

                ArrayList<String> strings = new ArrayList<>();
                strings.addAll(complexTypeName);
                strings.addAll(simpleTypeName);

                // 判断类型
                if (isNewType(strings, type)) { // 新定义的类型
                    // 存储数据
                    complexTypeObjectProperties.put(domainName, value);
                    // 创建关系
                    ObjectProperty objectProperty = model.createObjectProperty(ns + value);
                    objectProperty.setDomain(complexTypeClasses.get(domainName));
                    objectProperty.addRange(simpleTypeClasses.get(type) == null ? complexTypeClasses.get(type) : simpleTypeClasses.get(type));
                } else {
                    // 存储数据
                    complexTypeDataProperties.put(domainName, value);
                    // 创建属性
                    DatatypeProperty datatypeProperty = model.createDatatypeProperty(ns + value);
                    datatypeProperty.setDomain(complexTypeClasses.get(domainName));
                    datatypeProperty.setRange(ResourceFactory.createResource("xsd:" + type));
                }
            }
        }
        return model;
    }


    /**
     * @Description: 判断是否为新定义的类型
     */
    public Boolean isNewType(List<String> lists, String type) {
        return lists.contains(type);
    }

    /**
     * @Description: 预处理
     */
    public String preHandle(String str) {
        // 剔除语义无关的字符
        String string = str.replaceAll(String.valueOf('-'), "").replaceAll(String.valueOf('_'), "").replaceAll(String.valueOf(':'), "").replaceAll(String.valueOf('#'), "");
        // 忽略大小写
        return string.toLowerCase();
    }


}
