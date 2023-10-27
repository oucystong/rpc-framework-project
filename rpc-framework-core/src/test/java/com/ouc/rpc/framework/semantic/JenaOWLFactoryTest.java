package com.ouc.rpc.framework.semantic;

import com.ouc.rpc.framework.semantic.impl.jena.OWLKnowledgeBaseImpl;
import com.ouc.rpc.framework.semantic.owl.OWLKnowledgeBase;
import com.ouc.rpc.framework.semantic.owl.OWLOntology;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Mr.Tong
 */
public class JenaOWLFactoryTest {

    @Test
    void testJenaOWLFactory() {
        OWLKnowledgeBase kb = new OWLKnowledgeBaseImpl();
        OWLOntology ontology = kb.createOntology();
        System.out.println(ontology.getURI());

    }
}
