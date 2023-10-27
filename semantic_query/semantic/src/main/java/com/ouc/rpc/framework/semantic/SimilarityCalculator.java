package com.ouc.rpc.framework.semantic;

import com.ouc.rpc.framework.util.OntologyUtils;
import com.ouc.rpc.framework.util.WordNetUtils;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 相似度求解计算器
 * @Author: Mr.Tong
 */
public class SimilarityCalculator {

    /**
     * @Description: 待映射的临时本体模型
     */
    private OntModel srcOntologyModel;

    /**
     * @Description: 特定领域本体模型
     */
    private OntModel tarOntologyModel;


    public SimilarityCalculator() {
    }

    /**
     * @Description: 构造器
     */
    public SimilarityCalculator(String srcOntFilePath, String tarOntFilePath) {
        srcOntologyModel = ModelFactory.createOntologyModel();
        srcOntologyModel.read(srcOntFilePath);
        tarOntologyModel = ModelFactory.createOntologyModel();
        tarOntologyModel.read(tarOntFilePath);
    }

    /**
     * @Description: 构造器
     */
    public SimilarityCalculator(OntModel srcOntologyModel, OntModel tarOntologyModel) {
        this.srcOntologyModel = srcOntologyModel;
        this.tarOntologyModel = tarOntologyModel;
    }


    /**
     * @Description: 构造器
     */
    public SimilarityCalculator(OntModel srcOntologyModel, String tarOntFilePath) {
        this.srcOntologyModel = srcOntologyModel;
        tarOntologyModel = ModelFactory.createOntologyModel();
        tarOntologyModel.read(tarOntFilePath);
    }

    /**
     * @Description: 构造器
     */
    public SimilarityCalculator(String srcOntFilePath, OntModel tarOntologyModel) {
        srcOntologyModel = ModelFactory.createOntologyModel();
        srcOntologyModel.read(srcOntFilePath);
        this.tarOntologyModel = tarOntologyModel;
    }


    /**
     * @Description: 使用sigmod函数将数值转化到0-1之间
     */
    public Double getSimilarity(Double score) {
        return normalizeToZeroOne(score);
    }

    /**
     * @Description: 基于概念名称的相似度计算
     */
    public Double getConceptSimilarity(OntClass srcOntClass, OntClass tarOntClass) {

        // 获取概念名称
        String srcConceptSimpleName = OntologyUtils.getConceptSimpleName(srcOntClass);
        String tarConceptSimpleName = OntologyUtils.getConceptSimpleName(tarOntClass);

        // 采用编辑距离法计算两个概念名称关于字符的相似度
        Double conceptEditDistanceScore = getCharSimilarity(srcConceptSimpleName, tarConceptSimpleName);

        // 采用基于语义词典 WordNet 的计算方法来计算两个概念名称关于语义的相似度
        Double semanticSimilarityScore = WordNetUtils.getSemanticSimilarity(srcConceptSimpleName, tarConceptSimpleName);

        return conceptEditDistanceScore * 0.2 + 0.8 * semanticSimilarityScore;
    }


    /**
     * @Description: 基于属性的相似度计算
     */
    public Double getAttributeSimilarity(OntClass srcOntClass, List<String> srcProperties, OntClass tarOntClass) {

        // 获取目标类的属性
        List<String> targetProperties = getPropertiesOfClass(tarOntClass);

        // 有效性判断
        if (srcProperties == null || srcProperties.size() == 0 || targetProperties.size() == 0) {
            return 0.0;
        }

        // 两个类都存在属性或者关系 | 进行基于属性的相似性得分的求解
        Double weight = 1.0 / (targetProperties.size() * srcProperties.size());

        Double result = 0.0;

        // 遍历属性获取最大值得分
        for (String item1 : srcProperties) {
            double score = 0.0;
            for (String item2 : targetProperties) {
                // 计算得分
                Double charSimilarity = getCharSimilarity(item1, item2);
                Double semanticSimilarity = WordNetUtils.getSemanticSimilarity(item1, item2);
                double newScore = 0.2 * charSimilarity + 0.8 * semanticSimilarity;
                score += newScore * weight;
            }
            result += score;
        }

        return result;
    }


    /**
     * @Description: 基于结构的相似度计算
     */
    public Double getStructureSimilarity(OntClass srcOntClass, OntClass tarOntClass) {

        // 获取两个概念的直接父类集合
        List<OntClass> srcClassParentClass = srcOntClass.listSuperClasses(true).filterKeep(ontClass -> !ontClass.isAnon()).toList();
        List<OntClass> tarClassParentClass = tarOntClass.listSuperClasses(true).filterKeep(ontClass -> !ontClass.isAnon()).toList();

        // 获取两个概念的直接兄弟集合
        List<OntClass> srcClassBrotherClass = new ArrayList<>();
        List<OntClass> tarClassBrotherClass = new ArrayList<>();

        if (srcClassParentClass != null && srcClassParentClass.size() != 0) {
            List<OntClass> ontClasses = srcClassParentClass.get(0).listSubClasses(true).toList();
            for (OntClass ontClass : ontClasses) {
                if (!ontClass.getLocalName().equals(srcOntClass.getLocalName()) && !ontClass.isAnon()) {
                    srcClassBrotherClass.add(ontClass);
                }
            }
        }

        if (tarClassParentClass != null && tarClassParentClass.size() != 0) {
            List<OntClass> ontClasses = tarClassParentClass.get(0).listSubClasses(true).toList();
            for (OntClass ontClass : ontClasses) {
                if (!ontClass.getLocalName().equals(tarOntClass.getLocalName()) && !ontClass.isAnon()) {
                    tarClassBrotherClass.add(ontClass);
                }
            }
        }

        // 获取两个概念的直接子类集合
        List<OntClass> srcClassChildClass = srcOntClass.listSubClasses(true).filterKeep(ontClass -> !ontClass.isAnon()).toList();
        List<OntClass> tarClassChildClass = tarOntClass.listSubClasses(true).filterKeep(ontClass -> !ontClass.isAnon()).toList();

        Double parentScore = 0.0;
        Double brotherScore = 0.0;
        Double childScore = 0.0;

        // 直接父类求解得分
        if (srcClassParentClass != null && srcClassParentClass.size() != 0 && tarClassParentClass != null && tarClassParentClass.size() != 0) {
            parentScore = getStructureConceptSimilarity(srcClassParentClass.get(0), tarClassParentClass.get(0));
        }

        // 直接兄弟求解得分
        if (srcClassBrotherClass != null && srcClassBrotherClass.size() != 0 && tarClassBrotherClass != null && tarClassBrotherClass.size() != 0) {
            for (OntClass classBrotherClass : srcClassBrotherClass) {
                for (OntClass brotherClass : tarClassBrotherClass) {
                    Double newScore = getStructureConceptSimilarity(classBrotherClass, brotherClass);
                    if (newScore >= brotherScore) {
                        brotherScore = newScore;
                    }
                }
            }
        }


        // 直接子类求解得分
        if (srcClassChildClass != null && srcClassChildClass.size() != 0 && tarClassChildClass != null && tarClassChildClass.size() != 0) {
            for (OntClass classChildClass : srcClassChildClass) {
                for (OntClass childClass : tarClassChildClass) {
                    Double newScore = getStructureConceptSimilarity(classChildClass, childClass);
                    if (newScore >= childScore) {
                        childScore = newScore;
                    }
                }
            }
        }


        // 综合结构得分
        return parentScore * 0.5 + brotherScore * 0.25 + childScore * 0.25;
    }


    /**
     * @Description: 采用编辑距离法计算两个概念名称关于字符的相似度
     */
    public Double getCharSimilarity(String srcConceptSimpleName, String tarConceptSimpleName) {
        // 1 获取两者的最大长度
        Integer maxLength = srcConceptSimpleName.length() > tarConceptSimpleName.length() ? srcConceptSimpleName.length() : tarConceptSimpleName.length();
        // 2 最小编辑操作次数 | 使用动态规划解决
        Integer miniEditDistance = calculateEditDistance(srcConceptSimpleName, tarConceptSimpleName);
        // 3 最长公共子串的长度 | 使用动态规划解决
        Integer lcsLength = calculateLCSLength(srcConceptSimpleName, tarConceptSimpleName);
        // 4 求解结果
        Double score1 = 1 - (miniEditDistance / (double) maxLength);
        Double score2 = lcsLength / (double) maxLength;
        return (score1 + score2) / 2.0;
    }


    /**
     * @Description: 使用动态规划求解最小编辑距离
     */
    public int calculateEditDistance(String str1, String str2) {
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

    /**
     * @Description: 使用动态规划求解最长公共子串的长度
     */
    public int calculateLCSLength(String str1, String str2) {

        int maxLength = 0; // 最长公共子串的长度

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return maxLength;
    }


    /**
     * @Description: 获取类结构中的两个概念相似度
     */
    private Double getStructureConceptSimilarity(OntClass srcOntClass, OntClass tarOntClass) {

//        Double conceptSimilarity = getConceptSimilarity(srcOntClass, tarOntClass);
//
//        Double attributeSimilarity = getAttributeSimilarity(srcOntClass, tarOntClass);
//
//        return 0.65 * conceptSimilarity + 0.35 * attributeSimilarity;

        return null;
    }


    private double normalizeToZeroOne(double x) {
        double sigmoidValue = sigmoid(x);
        return (sigmoidValue - 0.5) * 2; // 将值映射到0-1范围
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }


    /**
     * @Description: 向上递归查询某个类的所有属性 | 包括继承的属性
     */
    private List<String> getPropertiesOfClass(OntClass ontClass) {
        // 查看所有的直接父类
        List<OntClass> superClasses = ontClass.listSuperClasses(true).toList();
        if (superClasses.size() == 0) {
            return new ArrayList<>();
        }
        // 遍历每一个父类 | 查找父类的属性
        ArrayList<String> result = new ArrayList<>();
        for (OntClass superClass : superClasses) {
            if (superClass.isRestriction()) {
                Restriction restriction = superClass.asRestriction();
                // String localName1 = restriction.asAllValuesFromRestriction().getAllValuesFrom().getLocalName();
                String localName = restriction.getOnProperty().getLocalName();
                result.add(localName);
            } else {
                result.addAll(getPropertiesOfClass(superClass));
            }
        }
        return result;
    }


}
