package com.ouc.rpc.framework.semantic;


import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.List;

/**
 * @Description: 本体解析器
 * @Author: Mr.Tong
 */
public class OntologyParser {

    /**
     * @Description: 获取本体的命名空间
     */
    public static String getTNSOfOntology(String ontologyFilePath) {
        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(ontologyFilePath);
        return ontModel.getNsPrefixURI("");
    }


    /**
     * @Description: 获取本体的所有概念
     */
    public static List<OntClass> getClassesOfOntology(String ontologyFilePath) {
        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(ontologyFilePath);
        return ontModel.listClasses().filterKeep(s -> !s.isAnon()).toList();
    }

    /**
     * @Description: 获取本体的具体类
     */
    public static OntClass getClassOfOntology(String ontologyFilePath, String owlType) {
        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(ontologyFilePath);
        return ontModel.getOntClass(owlType);
    }

}
