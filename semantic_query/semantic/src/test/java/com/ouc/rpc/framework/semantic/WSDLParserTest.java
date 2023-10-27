package com.ouc.rpc.framework.semantic;

import com.ouc.rpc.framework.util.WordNetUtils;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.neo4j.driver.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.wsdl.WSDLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class WSDLParserTest {


//    @Test
//    public void testGetServiceName() throws WSDLException {
//        WSDLParser wsdlParser = new WSDLParser();
//        HashMap<String, String> serviceInfoName = wsdlParser.getServiceInfoName("http://localhost:80/wsdl/novel_author_service.wsdl");
//
//    }

//    @Test
//    public void testGetServiceOperations() throws WSDLException {
//        WSDLParser wsdlParser = new WSDLParser();
//        ArrayList<String> serviceOperations = wsdlParser.getServiceOperations("http://localhost:80/wsdl/novel_author_service.wsdl");
//
//    }

//    @Test
//    public void testGetInAndOutOfOperation() throws WSDLException {
//        WSDLParser wsdlParser = new WSDLParser();
//        wsdlParser.getInAndOutOfOperation("get_AUTHOR", "http://localhost:80/wsdl/novel_author_service.wsdl");
//    }

    @Test
    public void testJavaWsdlApi() throws WSDLException {
        try {
            // 创建 DOM 解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建 DOM 解析器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 解析 XML 文档
            Document document = builder.parse("http://localhost:80/wsdl/novel_author_service.wsdl");
            NodeList elementsByTagName = document.getElementsByTagName("xsd:complexType");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Node item = elementsByTagName.item(i);
                System.out.println(((DeferredElementImpl) item).getAttribute("name"));
            }
            System.out.println("===");
            // 进行进一步的处理，如遍历子元素、获取属性值等
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testUnionOWL() throws FileNotFoundException {
//        OntologyParser ontologyParser = new OntologyParser();
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("http://127.0.0.1/unionOWL/Service.owl");
//        strings.add("http://127.0.0.1/unionOWL/Profile.owl");
//        strings.add("http://127.0.0.1/unionOWL/Process.owl");
//        strings.add("http://127.0.0.1/unionOWL/Grounding.owl");
////        strings.add("http://127.0.0.1/unionOWL/ProfileAdditionalParameters.owl");
////        strings.add("http://127.0.0.1/unionOWL/ProfileDeprecatedElements.owl");
////        strings.add("http://127.0.0.1/unionOWL/Resource.owl");
////        strings.add("http://127.0.0.1/unionOWL/time-entry.owl");
////        strings.add("http://127.0.0.1/unionOWL/ActorDefault.owl");
//
//        ontologyParser.unionOWL(strings);
//    }

    @Test
    public void testGetOntologyModel() {
//        OntologyParser ontologyParser = new OntologyParser("http://127.0.0.1/test/books.owl");
//        ontologyParser.listOntAllClass();
    }


//    @Test
//    public void testSimilarityCalculator() {
//        SimilarityCalculator similarityCalculator = new SimilarityCalculator("http://127.0.0.1/test/myontology.owl", "http://127.0.0.1/test/books.owl");
//
//        ExtendedIterator<OntClass> ontClassExtendedIterator = similarityCalculator.getSrcOntologyModel().listClasses();
//        ExtendedIterator<OntClass> ontClassExtendedIterator1 = similarityCalculator.getTarOntologyModel().listClasses();
//
//        ArrayList<OntClass> ontClassesOne = new ArrayList<>();
//        ArrayList<OntClass> ontClassesTwo = new ArrayList<>();
//
//        // 遍历并打印所有的类名
//        while (ontClassExtendedIterator.hasNext()) {
//            OntClass ontClass = ontClassExtendedIterator.next();
//            if (!ontClass.isAnon()) {
//                ontClassesOne.add(ontClass);
//            }
//        }
//
//        // 遍历并打印所有的类名
//        while (ontClassExtendedIterator1.hasNext()) {
//            OntClass ontClass = ontClassExtendedIterator1.next();
//            // 去除导入的类
//            if (!ontClass.isAnon()) {
//                ontClassesTwo.add(ontClass);
////                System.out.println("Class: " + ontClass.getLocalName());
//            }
//        }
//
//        for (OntClass ontClass : ontClassesOne) {
//            Double score = 0.0;
//            OntClass tmp = null;
//            for (OntClass aClass : ontClassesTwo) {
//                Double conceptSimilarity = similarityCalculator.getConceptSimilarity(ontClass, aClass);
//                if (conceptSimilarity >= score) {
//                    score = conceptSimilarity;
//                    tmp = aClass;
//                }
//            }
//            System.out.println(ontClass.getLocalName() + " " + tmp.getLocalName() + " " + "Score: " + score);
//        }
//    }

    @Test
    public void testCalculateEditDistance() {
        String str1 = "Trade";
        String str2 = "Deal";

        int distance = calculateEditDistance(str1, str2);

        System.out.println("Minimum edit distance: " + distance);

    }


    private static int calculateEditDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    int replace = dp[i - 1][j - 1] + 1;
                    dp[i][j] = Math.min(Math.min(insert, delete), replace);
                }
            }
        }
        return dp[m][n];
    }


    @Test
    public void testGetSynonyms() {
        Double semanticSimilarity = WordNetUtils.getSemanticSimilarity("_Novel", "Novel");
        System.out.println("得分: " + semanticSimilarity);
    }


    @Test
    public void testCreateTmpOntology() throws FileNotFoundException {
        // 创建一个新的本体模型
        OntModel model = ModelFactory.createOntologyModel();

        // 命名空间和前缀
        String baseURI = "http://example.com/myontology";
        String ns = baseURI + "#";
        String prefix = "";

        // 设置命名空间前缀
        model.setNsPrefix(prefix, ns);

        // 创建本体类和属性
        model.createClass(ns + "_Novel");
        model.createClass(ns + "_Author");

        // 保存本体为OWL文件
        String filename = "myontology.owl";
//        model.write(System.out, "RDF/XML-ABBREV"); // 输出到控制台
        model.write(new FileOutputStream(filename), "RDF/XML-ABBREV"); // 保存到文件

        System.out.println("Ontology created and saved as " + filename);
    }


    @Test
    public void testCreateTmpOntology1() throws FileNotFoundException {
        // 创建一个新的本体模型
        OntModel model = ModelFactory.createOntologyModel();

        // 命名空间和前缀
        String baseURI = "http://example.com/myontology";
        String ns = baseURI + "#";
        String prefix = "";

        // 设置命名空间前缀
        model.setNsPrefix(prefix, ns);

        // 创建本体类和属性
        OntClass aClass = model.createClass(ns + "Novel");
        Property property = model.createProperty(ns + "name");
        Property property1 = model.createProperty(ns + "desc");
        Property property2 = model.createProperty(ns + "author");
        Property property3 = model.createProperty(ns + "price");
        aClass.addProperty(property, "ttt");
        aClass.addProperty(property1, "yyy");
        aClass.addProperty(property2, "zzz");
        aClass.addProperty(property3, "ssss");

        OntClass aClass1 = model.createClass(ns + "Author");
        Property property4 = model.createProperty(ns + "name");
        Property property5 = model.createProperty(ns + "age");
        Property property6 = model.createProperty(ns + "gender");
        aClass1.addProperty(property4, "dasd");
        aClass1.addProperty(property5, "dasdd");
        aClass1.addProperty(property6, "dasdjk");


        // 保存本体为OWL文件
        String filename = "myontology1.owl";
//        model.write(System.out, "RDF/XML-ABBREV"); // 输出到控制台
        model.write(new FileOutputStream(filename), "RDF/XML-ABBREV"); // 保存到文件

        System.out.println("Ontology created and saved as " + filename);
    }


    @Test
    public void testCreateTmpOntology2() throws FileNotFoundException {
        // 创建一个新的本体模型
        OntModel model = ModelFactory.createOntologyModel();

        // 命名空间和前缀
        String baseURI = "http://example.com/myontology";
        String ns = baseURI + "#";
        String prefix = "";

        // 设置命名空间前缀
        model.setNsPrefix(prefix, ns);

        // 创建本体类和属性
        OntClass aClass = model.createClass(ns + "_Novel");
        Property property = model.createProperty(ns + "name");
        Property property1 = model.createProperty(ns + "desc");

        aClass.addProperty(property, "ttt");
        aClass.addProperty(property1, "yyy");

        OntClass aClass1 = model.createClass(ns + "_Author");
        Property property4 = model.createProperty(ns + "name");
        Property property5 = model.createProperty(ns + "age");
        aClass1.addProperty(property4, "dasd");
        aClass1.addProperty(property5, "dasdd");


        StringWriter writer = new StringWriter();
        // 保存本体为OWL文件
        String filename = "myontology2.owl";
//        model.write(System.out, "RDF/XML-ABBREV"); // 输出到控制台
//        model.write(new FileOutputStream(filename), "RDF/XML-ABBREV"); // 保存到文件
        model.write(writer, "RDF/XML-ABBREV"); // 保存到文件
        String owlContent = writer.toString();
        System.out.println(owlContent);

//        System.out.println("Ontology created and saved as " + filename);
    }


//    @Test
//    public void testAttributeSimilarityCalculator() {
//        SimilarityCalculator similarityCalculator = new SimilarityCalculator("http://127.0.0.1/test/myontology2.owl", "http://127.0.0.1/test/myontology1.owl");
//
//        ExtendedIterator<OntClass> ontClassExtendedIterator = similarityCalculator.getSrcOntologyModel().listClasses();
//        ExtendedIterator<OntClass> ontClassExtendedIterator1 = similarityCalculator.getTarOntologyModel().listClasses();
//
//        ArrayList<OntClass> ontClassesOne = new ArrayList<>();
//        ArrayList<OntClass> ontClassesTwo = new ArrayList<>();
//
//        // 遍历并打印所有的类名
//        while (ontClassExtendedIterator.hasNext()) {
//            OntClass ontClass = ontClassExtendedIterator.next();
//            if (!ontClass.isAnon()) {
//                ontClassesOne.add(ontClass);
//            }
//        }
//
//        // 遍历并打印所有的类名
//        while (ontClassExtendedIterator1.hasNext()) {
//            OntClass ontClass = ontClassExtendedIterator1.next();
//            // 去除导入的类
//            if (!ontClass.isAnon()) {
//                ontClassesTwo.add(ontClass);
////                System.out.println("Class: " + ontClass.getLocalName());
//            }
//        }
//
//        for (OntClass ontClass : ontClassesOne) {
//            Double score = 0.0;
//            OntClass tmp = null;
//            for (OntClass aClass : ontClassesTwo) {
//                Double attributeSimilarity = similarityCalculator.getAttributeSimilarity(ontClass, aClass);
//                if (attributeSimilarity >= score) {
//                    score = attributeSimilarity;
//                    tmp = aClass;
//                }
//            }
//            System.out.println(ontClass.getLocalName() + " " + tmp.getLocalName() + " " + "Score: " + score);
//        }
//    }

    @Test
    public void testSigmod() {
        Double sum = 0 + 0 + 2.5689;
        System.out.println(sigmoid(sum));
    }


    @Test
    public void testGuiyihua() {
        SimilarityCalculator similarityCalculator = new SimilarityCalculator("http://127.0.0.1/test/myontology2.owl", "http://127.0.0.1/test/myontology1.owl");

//        System.out.println(similarityCalculator.similarity(new double[]{3.45, 4.23, 3.23}, new double[]{0.2, 0.7, 0.1}));
    }


    @Test
    public void testWSDL2OWL() {
//        WSDL2OWL wsdl2OWL = new WSDL2OWL("http://localhost:80/test/novel_author_service.wsdl");
//        wsdl2OWL.getTmpOntologyFromWSDL();

    }


    @Test
    public void testCreateOnt() {

        // 创建一个新的本体模型
        OntModel model = ModelFactory.createOntologyModel();

        // 命名空间和前缀
        String baseURI = "http://example.com/myontology";
        String ns = baseURI + "#";
        String prefix = "";

        // 设置命名空间前缀
        model.setNsPrefix(prefix, ns);

        // 创建类：Novel
        OntClass novelClass = model.createClass(ns + "Novel");

        // 创建类：Book
        OntClass bookClass = model.createClass(ns + "Book");

        // 创建类关系：Novel 是 Medium 的子类
        OntClass mediumClass = model.createClass(ns + "Medium");

        // 创建限制：allValuesFrom Medium，onProperty hasSize
        OntProperty hasSizeProperty = model.createOntProperty(ns + "hasSize");

        // 创建类关系：Novel 是 Book 的子类
        novelClass.addSuperClass(bookClass);


        Restriction restriction = model.createAllValuesFromRestriction(null, hasSizeProperty, mediumClass);

        // 将限制添加到 Novel 类的子类关系中
        novelClass.addSuperClass(restriction);

        // 输出本体内容
        model.write(System.out, "RDF/XML-ABBREV");
    }


    @Test
    public void testDom4J() {

        SAXReader saxReader = new SAXReader();
        org.dom4j.Document document = null;
        try {
            document = saxReader.read(new URL("http://localhost:80/wsdl/novel_author_service.wsdl"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Namespace xsdNamespace = new Namespace("xsd", "http://www.w3.org/2001/XMLSchema");
        List<Element> elements = document.getRootElement().elements("xsd:simpleType");


    }


    @Test
    public void test23() {
        double[] data = {5.0, 10.0, 3.0, 8.0, 2.0};

        double mean = 0.0;
        double stdDev = 0.0;

        // 计算均值
        for (double value : data) {
            mean += value;
        }
        mean /= data.length;

        // 计算标准差
        for (double value : data) {
            stdDev += Math.pow(value - mean, 2);
        }
        stdDev = Math.sqrt(stdDev / data.length);

        // 进行Z-Score标准化
        double[] normalizedData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalizedData[i] = (data[i] - mean) / stdDev;
        }

        // 输出标准化后的数据
        for (double value : normalizedData) {
            System.out.println(value);
        }
    }

    @Test
    public void testName() {


        double x = 0.25; // 要转化的值
        double sigmoidValue = sigmoid(x);
        double normalizedValue = normalizeToZeroOne(x);

        System.out.println("Sigmoid value: " + sigmoidValue);
        System.out.println("Normalized value (0-1): " + normalizedValue);

    }


    private double normalizeToZeroOne(double x) {
        double sigmoidValue = sigmoid(x);
        return (sigmoidValue - 0.5) * 2; // 将值映射到0-1范围
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }


    @Test
    public void testNovel() {
        Double semanticSimilarity = WordNetUtils.getSemanticSimilarity("Trade", "Deal");
        SimilarityCalculator similarityCalculator = new SimilarityCalculator();
        int i = similarityCalculator.calculateEditDistance("Trade", "Deal");
        int j = similarityCalculator.calculateLCSLength("Trade", "Deal");
        Double charSimilarity = similarityCalculator.getCharSimilarity("Trade", "Deal");
        System.out.println(semanticSimilarity);
        System.out.println(charSimilarity);
        System.out.println(i);
        System.out.println(j);
    }

    @Test
    public void testOWLOntology() {
        OntModel tarOntModel = ModelFactory.createOntologyModel();
        tarOntModel.read("http://127.0.0.1/ontology/books.owl");
        OntClass ontClass = tarOntModel.getOntClass("http://127.0.0.1:8000/ontology/books.owl#FantansyNovel");
        System.out.println(getPropertiesOfClass(ontClass));

    }

    // 递归查询
    private List<String> getPropertiesOfClass(OntClass ontClass) {
        // 查看所有的父类
        List<OntClass> superClasses = ontClass.listSuperClasses(true).toList();
        if (superClasses.size() == 0) {
            return new ArrayList<>();
        }
        // 遍历每一个父类 | 查找父类的属性
        ArrayList<String> result = new ArrayList<>();
        for (OntClass superClass : superClasses) {
            if (superClass.isRestriction()) {
                Restriction restriction = superClass.asRestriction();
//                String localName1 = restriction.asAllValuesFromRestriction().getAllValuesFrom().getLocalName();
                String localName = restriction.getOnProperty().getLocalName();
                result.add(localName);
            } else {
                result.addAll(getPropertiesOfClass(superClass));
            }
        }
        return result;
    }


    @Test
    public void test12345() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");
        System.out.println(new WSDL2OWL(null).isNewType(strings, "a"));
        System.out.println(new WSDL2OWL(null).preHandle("ASKJHDjjj"));
    }

    @Test
    public void test67() {
        String[][] strings = new String[6][2];
        strings[0][0] = "publication";
        strings[0][1] = "Publication";
        strings[1][0] = "author";
        strings[1][1] = "Author";
        strings[2][0] = "price";
        strings[2][1] = "Price";
        strings[3][0] = "publication";
        strings[3][1] = "Price";
        strings[4][0] = "author";
        strings[4][1] = "Publication";
        strings[5][0] = "price";
        strings[5][1] = "Author";
        SimilarityCalculator similarityCalculator = new SimilarityCalculator();

//        for (int i = 0; i < 6; i++) {
//            String src = strings[i][0];
//            String tar = strings[i][1];
//            int max = src.length() > tar.length() ? src.length() : tar.length();
//            double v = 1 - similarityCalculator.calculateEditDistance(src, tar) / (double) max;
//            double v1 = similarityCalculator.calculateLCSLength(src, tar) / (double) max;
//            Double v3 = similarityCalculator.getCharSimilarity(src, tar);
//            System.out.println("edit: " + v + " lcs: " + v1 + " aver: " + v3);
//        }

//        System.out.println(similarityCalculator.calculateLCSLength(str1, str2));
//        System.out.println(similarityCalculator.calculateEditDistance(str1, str2));
    }


    @Test
    public void test89() {
        double number = 123.456789;

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String formattedNumber = decimalFormat.format(number);

        System.out.println("Formatted number: " + formattedNumber);
    }

    @Test
    public void testIcon() {
        // 显示带有信息图标的提示框
//        JOptionPane.showMessageDialog(null, "This is an information message", "Information",
//                JOptionPane.INFORMATION_MESSAGE);
//
//        // 显示带有警告图标的提示框
//        JOptionPane.showMessageDialog(null, "This is a warning message", "Warning",
//                JOptionPane.WARNING_MESSAGE);
//
//        // 显示带有错误图标的提示框
//        JOptionPane.showMessageDialog(null, "This is an error message", "Error",
//                JOptionPane.ERROR_MESSAGE);
//
//        // 显示带有问题图标的提示框
//        JOptionPane.showMessageDialog(null, "This is a question message", "Question",
//                JOptionPane.QUESTION_MESSAGE);

        // 显示自定义图标的提示框
        // 注意：要使用自定义图标，需要将图标文件放在项目的资源目录中，并提供正确的图标路径
//        JOptionPane.showMessageDialog(null, "Custom icon message", "Custom Icon",
//                JOptionPane.INFORMATION_MESSAGE, new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/OK.png"));

        // 加载自定义图标
        ImageIcon customIcon = new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/info.png");

        // 设置自定义图标的大小
        Image image = customIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        customIcon.setImage(scaledImage);

        // 显示带有自定义大小图标的提示框
        JOptionPane.showMessageDialog(null, "This is a custom sized icon message", "Custom Sized Icon", JOptionPane.INFORMATION_MESSAGE, customIcon);
    }


    @Test
    public void testOWL_S() {
        // 创建一个空的本体模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // 创建导入的本体模型
        OntModel importModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        importModel.read("http://127.0.0.1/unionOWL/OWL-S-1.0-Union-Ontology-1.0.0.owl");
        ontModel.setNsPrefix("srpc", "http://www.srpc.com/owl-s-1.0-union-ontology#");
        ontModel.addSubModel(importModel);

        // 创建本体的命名空间
        String namespace = "http://example.org/myontology#";
        ontModel.setNsPrefix("ttt", namespace);

        // 创建本体
        Ontology ontology = ontModel.createOntology(namespace);
        ontology.addImport(ResourceFactory.createResource("http://www.srpc.com/owl-s-1.0-union-ontology"));

        OntClass ontClass = ontModel.getOntClass("http://www.srpc.com/owl-s-1.0-union-ontology#Output");
        System.out.println(ontClass.getLocalName());
        Individual individual = ontClass.createIndividual(namespace + "Test");

        System.out.println(ontModel.listIndividuals().toList());

        System.out.println(ontModel.listClasses().filterKeep(s -> !s.isAnon()).toList());

        ontModel.write(System.out, "RDF/XML-ABBREV");


    }


    @Test
    public void test098709() {
        // 创建一个空的本体模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // 设置命名空间前缀
        String namespace = "http://127.0.0.1:8000/wsdl/NovelAuthor#";
        ontModel.setNsPrefix("Test", namespace);

        // 创建 OWL 类和属性
        OntClass mediumClass = ontModel.createClass(namespace + "medium");
        OntClass authorClass = ontModel.createClass(namespace + "author");
        OntClass novelClass = ontModel.createClass(namespace + "novel");
        ObjectProperty hasSizeProperty = ontModel.createObjectProperty(namespace + "hassize");

        // 设置属性的范围和域
        hasSizeProperty.addRange(mediumClass);
        hasSizeProperty.addDomain(novelClass);

        // 保存本体模型到文件
        String outputFile = "path/to/output.owl";
        ontModel.write(System.out, "RDF/XML"); // 打印到控制台

    }


    /**
     * @Description: 重要
     */
    @Test
    public void test87678() {
        // 创建一个空的本体模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // 设置命名空间前缀
        ontModel.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        ontModel.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
        ontModel.setNsPrefix("Test", "http://127.0.0.1:8000/wsdl/NovelAuthor#");
        ontModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        ontModel.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");

        // 创建 OWL 类
        OntClass mediumClass = ontModel.createClass("Test:medium");
        OntClass authorClass = ontModel.createClass("Test:author");
        OntClass novelClass = ontModel.createClass("Test:novel");

        // 创建 OWL 属性
        ObjectProperty hasSizeProperty = ontModel.createObjectProperty("Test:hassize");
        hasSizeProperty.addRange(mediumClass);
        hasSizeProperty.addDomain(novelClass);

        // 保存本体模型到文件

        ontModel.write(System.out, "RDF/XML"); // 打印到控制台

        ontModel.listClasses().toList().forEach(s -> System.out.println(s.getLocalName()));
    }

//
//    @Test
//    public void testsaaada() {
//        System.out.println(Integer.highestOneBit(33));
//    }

    @Test
    public void testRegister() {


        // 连接到 Neo4j 数据库
        String uri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "tongyusheng";
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
             Session session = driver.session()) {

            // 插入实例数据
//            session.run("CREATE (:Person {name: 'John', age: 25})");
//            session.run("CREATE (:Person {name: 'Alice', age: 30})");
//            session.run("CREATE (:Person {name: 'Bob', age: 35})");
//            session.run("MATCH (node1:Person {name: 'Bob'}), (node2:Person {name: 'John'})\n" +
//                    "CREATE (node1)-[:RELATIONSHIP]->(node2)");
//
//            session.run("CREATE (:Service {name: 'AuthorPublicationpriceService',referName: 'http://127.0.0.1:8000/wsdl/AuthorPublicationprice/owls#AuthorPublicationpriceService'})");
//
//
//             定义规则和查询
//            String query = "MATCH (p1:Person)-[:RELATIONSHIP]->(p2:Person) WHERE p1.age > p2.age RETURN p1.name, p2.name";
//            String query = "MATCH (profile:ServiceProfile) WHERE size((profile)-[:has_process]->()) >= 1 RETURN profile.name";
//            String query = "MATCH (profile:ServiceProfile) WHERE size((profile)-[:has_process]->()) >= 1 WITH profile MATCH (profile)-[:has_process]->(atomic:AtomicProcess) WITH atomic MATCH ((atomic)-[:hasInput]->(in:Input)),((atomic)-[:hasOutput]->(out:Output)) WHERE in.parameterType CONTAINS 'Author' AND out.parameterType CONTAINS 'Author' RETURN profile.name";
            String query = "MATCH (profile:ServiceProfile) WHERE size((profile)-[:has_process]->()) >= 1 WITH profile MATCH (profile)-[:has_process]->(atomic:AtomicProcess) WITH atomic,profile MATCH ((atomic)-[:hasInput]->(in:Input)),((atomic)-[:hasOutput]->(out:Output)) WHERE in.parameterType CONTAINS 'Author' OR out.parameterType CONTAINS 'Author' RETURN DISTINCT profile.name";

            // 执行查询
            Result result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                String person1 = record.get("profile.name").asString();
//                String person2 = record.get("out.name").asString();
                System.out.println(person1);
//                System.out.println(person2);
            }
        }



    }
}

