package com.ouc.rpc.framework.semantic;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import javax.wsdl.*;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description: WSDL文件解析器
 * @Author: Mr.Tong
 */
public class WSDLParser {


    /**
     * @Description: 常量
     */
    public static final String SERVICE_NAME = "serviceName";
    public static final String SERVICE_DESC = "serviceDesc";
    public static final String SERVICE_TARGET_NAMESPACE = "targetNameSpace";
    public static final String INPUT_PARAMETERS = "input";
    public static final String OUTPUT_PARAMETERS = "output";
    public static final String WSDL_NAME = "wsdlName";
    public static final String WSDL_TYPE = "wsdlType";
    public static final String XSD_TYPE = "xsdType";
    public static final String DEFAULT_TYPE = "any";
    public static final String COMPLEX_TYPE = "complex";
    public static final String SIMPLE_TYPE = "simple";
    public static final String DEFAULT_VALUE = "-";
    /**
     * @Description: 标签名称
     */
    public static final String TAG_ELEMENT = "element";
    public static final String TAG_COMPLEX_TYPE = "complexType";
    public static final String TAG_SIMPLE_TYPE = "simpleType";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_TYPES = "types";
    public static final String TAG_SCHEMA = "schema";
    public static final String TAG_PART = "part";
    public static final String TAG_PORT_TYPE = "portType";
    public static final String TAG_OPERATION = "operation";
    public static final String TAG_INPUT = "input";
    public static final String TAG_OUTPUT = "output";
    public static final String TAG_SEQUENCE = "sequence";
    /**
     * @Description: 标签属性
     */
    public static final String ATTR_NAME = "name";
    public static final String ATTR_TYPE = "type";
    public static final String ATTR_ELEMENT = "element";
    public static final String ATTR_MESSAGE = "message";
    public static final String ATTR_TARGET_NAME_SPACE = "targetNameSpace";


    /**
     * @Description: WSDL文档模型 | DOM4J
     */
    private Document document;


    /**
     * @Description: WSDL文档模型 | WSDL4J
     */
    private Definition definition;


    /**
     * @Description: 构造器
     */
    public WSDLParser(String wsdlFilePath) {
        // 创建WSDL文档模型 | DOM4J
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new URL(wsdlFilePath));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        // 创建WSDL文档模型 | WSDL4J
        try {
            WSDLFactory wsdlFactory = WSDLFactory.newInstance();
            WSDLReader reader = wsdlFactory.newWSDLReader();
            definition = reader.readWSDL(wsdlFilePath);
        } catch (WSDLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 获取服务信息
     */
    public HashMap<String, String> getServiceInfoName() {
        HashMap<String, String> serviceInfo = new HashMap<>();

        Map services = definition.getServices();
        services.forEach((o, o2) -> {
            // 服务名称
            QName qname = (QName) o;
            serviceInfo.put(SERVICE_NAME, qname.getLocalPart());
        });
        serviceInfo.put(SERVICE_DESC, "Please fill in the service description information about " + definition.getQName().getLocalPart());
        serviceInfo.put(SERVICE_TARGET_NAMESPACE, definition.getTargetNamespace());

        return serviceInfo;
    }

    /**
     * @Description: 获取WSDL文件中的所有operation
     */
    public ArrayList<String> getServiceOperations() {
        ArrayList<String> operations = new ArrayList<>();

        Map portTypes = definition.getPortTypes();

        portTypes.forEach((o, o2) -> {
            PortType portType = (PortType) o2;
            portType.getOperations().forEach(o12 -> {
                Operation o1 = (Operation) o12;
                operations.add(o1.getName());
            });
        });

        return operations;
    }


    /**
     * @Description: 获取具体wsdl文件的具体operation的输入参数和输出参数
     */
    public HashMap<String, List<Map<String, String>>> getInAndOutOfOperation(String operation) {

        HashMap<String, Map> receiveInAndOut = new HashMap<>();

        Map portTypes = definition.getPortTypes();

        portTypes.forEach((o, o2) -> {
            PortType portType = (PortType) o2;
            portType.getOperations().forEach(new Consumer() {
                @Override
                public void accept(Object o) {
                    Operation op = (Operation) o;
                    if (op.getName().equals(operation)) {
                        receiveInAndOut.put(INPUT_PARAMETERS, op.getInput().getMessage().getParts());
                        receiveInAndOut.put(OUTPUT_PARAMETERS, op.getOutput().getMessage().getParts());
                    }
                }
            });
        });

        HashMap<String, List<Map<String, String>>> result = new HashMap<>();

        ArrayList<Map<String, String>> inputParts = new ArrayList<>();
        ArrayList<Map<String, String>> outputParts = new ArrayList<>();

        // handle input
        Map inputParametersMap = receiveInAndOut.get(INPUT_PARAMETERS);

        inputParametersMap.forEach((o, o2) -> {

            HashMap<String, String> item = new HashMap<>();

            Part part = (Part) o2;

            // wsdl name
            item.put(WSDL_NAME, part.getName());
            // wsdl type
            String wsdlType = getWSDLType(part.getTypeName().getLocalPart());
            item.put(WSDL_TYPE, wsdlType);
            // xsd type
            String xsdType = getXSDType(part.getTypeName().getLocalPart());
            item.put(XSD_TYPE, xsdType);

            inputParts.add(item);
        });

        // handle output
        Map outputParametersMap = receiveInAndOut.get(OUTPUT_PARAMETERS);

        outputParametersMap.forEach((o, o2) -> {

            HashMap<String, String> item = new HashMap<>();

            Part part = (Part) o2;

            // wsdl name
            item.put(WSDL_NAME, part.getName());
            // wsdl type
            String wsdlType = getWSDLType(part.getTypeName().getLocalPart());
            item.put(WSDL_TYPE, wsdlType);
            // xsd type
            String xsdType = getXSDType(part.getTypeName().getLocalPart());
            item.put(XSD_TYPE, xsdType);

            outputParts.add(item);
        });

        // handle result
        result.put(INPUT_PARAMETERS, inputParts);
        result.put(OUTPUT_PARAMETERS, outputParts);

        return result;
    }

    /**
     * @Description: 获取wsdl类型
     */
    public String getWSDLType(String wsdlType) {

        List<Element> elementList = getAllElementNodes();

        for (int i = 0; i < elementList.size(); i++) {
            String attrName = elementList.get(i).attribute(ATTR_NAME).getValue();
            String tmp = elementList.get(i).attribute(ATTR_TYPE).getValue();
            String attrType = tmp.contains(":") ? tmp.substring(tmp.indexOf(":") + 1) : tmp;
            if (attrType.equals(wsdlType)) {
                return attrName;
            }
        }

        return wsdlType;
    }


    /**
     * @Description: 查看参数类型
     */
    public String getXSDType(String wsdlType) {

        // complex type
        List<Element> allComplexNodes = getAllComplexNodes();
        for (Element complexNode : allComplexNodes) {
            if (complexNode.attribute(ATTR_NAME).getValue().equals(wsdlType)) {
                return "xsd:" + COMPLEX_TYPE;
            }
        }
        // simple type
        List<Element> allSimpleNodes = getAllSimpleNodes();
        for (Element simpleNode : allSimpleNodes) {
            if (simpleNode.attribute(ATTR_NAME).getValue().equals(wsdlType)) {
                return "xsd:" + SIMPLE_TYPE;
            }
        }

        return "xsd:" + DEFAULT_TYPE;
    }


    /**
     * @Description: 获取所有的element标签节点
     */
    public List<Element> getAllElementNodes() {
        List<Element> elements = document.getRootElement().element(TAG_TYPES).element(TAG_SCHEMA).elements(TAG_ELEMENT);
        return elements;
    }


    /**
     * @Description: 获取所有的complexType标签节点
     */
    public List<Element> getAllComplexNodes() {
        List<Element> elements = document.getRootElement().element(TAG_TYPES).element(TAG_SCHEMA).elements(TAG_COMPLEX_TYPE);
        return elements;
    }


    /**
     * @Description: 获取所有的simpleType标签节点
     */
    public List<Element> getAllSimpleNodes() {
        List<Element> elements = document.getRootElement().element(TAG_TYPES).element(TAG_SCHEMA).elements(TAG_SIMPLE_TYPE);
        return elements;
    }


}
