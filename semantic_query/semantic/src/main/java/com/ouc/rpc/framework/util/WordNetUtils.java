package com.ouc.rpc.framework.util;


import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.*;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.dictionary.Dictionary;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @Description: WordNet工具类
 * @Author: Mr.Tong
 */
public class WordNetUtils {

    /**
     * @Description: WordNet字典数据库
     */
    private static Dictionary dict;

    /**
     * @Description: 存储信息量数据
     */
    private static Map<String, Double> freq = new HashMap<String, Double>();


    static {
        // 创建WordNet字典
        try {
            JWNL.initialize(new FileInputStream("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/file_properties.xml"));
        } catch (JWNLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        dict = Dictionary.getInstance();


        // 读取信息量数据存储到freq中
        URL url = null;
        BufferedReader in = null;
        try {
            url = new URL("file:///Users/ethan/Desktop/2023/code/semantic/src/main/resources/ic-bnc-resnik-add1.dat");
            //open the info content file for reading
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            //get the first line from the file (should be the WordNet version info)
            String line = in.readLine();
            //Check that what we have is actually a file of IC values
            if (line == null || !line.startsWith("wnver::")) throw new IOException("Malformed InfoContent file");
            //Check that the IC file is meant for use with the version
            //of WordNet we are currently using
            if (!line.endsWith("::" + JWNL.getVersion().getNumber()))
                throw new Exception("InfoContent file version doesn't match WordNet version");
            //Initially set the IC values of the noun and verb roots to 0
            freq.put("n", 0d);
            freq.put("v", 0d);
            //Get the first line of real data ready for use
            line = in.readLine();
            while (line != null && !line.equals("")) {
                //while there is still data in the file to process...
                //split the line on the whitespace
                String[] data = line.split("\\s+");

                //store the frequency (2nd column) against the synset ID (1st column)
                freq.put(data[0], new Double(data[1]));

                if (data.length == 3 && data[2].equals("ROOT")) {
                    //if there are three columns on this line and the
                    //last one is ROOT then...

                    //get the POS tag of the synset
                    String pos = data[0].substring(data[0].length() - 1);

                    //updated the node frequency for the POS tag
                    freq.put(pos, Double.parseDouble(data[1]) + freq.get(pos));
                }

                //read in the next line from the file ready for processing
                line = in.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //if we managed to open the file then close it
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * @Description: 根据WordNet获取两个词语的相似度
     */
    public static Double getSemanticSimilarity(String srcWord, String tarWord) {

        Set<Synset> ss1 = getSynsets(srcWord);
        Set<Synset> ss2 = getSynsets(tarWord);

        // 默认两者的相似得分为0
        double score = 0;

        for (Synset s1 : ss1) {
            for (Synset s2 : ss2) {
                //for each pair of synsets get the similarity
                Double newScore = getJCNSimilarityScore(s1, s2);
//                Double newScore = getLINSimilarityScore(s1, s2);
                if (newScore >= score) {
                    score = newScore;
                }
            }
        }

//        double score = 0;
//
//        Double sumScore1 = 0.0;
//        for (Synset s1 : ss1) {
//            double score1 = 0.0;
//            for (Synset s2 : ss2) {
//                Double newScore = getJCNSimilarityScore(s1, s2);
//                if (newScore >= score1) {
//                    score1 = newScore;
//                }
//            }
//            sumScore1 += score1;
//        }
//
//        Double sumScore2 = 0.0;
//        for (Synset s2 : ss2) {
//            double score2 = 0.0;
//            for (Synset s1 : ss1) {
//                Double newScore = getJCNSimilarityScore(s2, s1);
//                if (newScore >= score2) {
//                    score2 = newScore;
//                }
//            }
//            sumScore2 += score2;
//        }
//
//        double sumScore = sumScore1 + sumScore2;
//        score = sumScore / (ss1.size() + ss2.size());
        return normalizeToZeroOne(score);
//        return score;
    }

    private static double normalizeToZeroOne(double x) {
        double sigmoidValue = sigmoid(x);
        return (sigmoidValue - 0.5) * 2; // 将值映射到0-1范围
    }

    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }


    /**
     * @Description: 获取词语的同义词词集
     */
    private static Set<Synset> getSynsets(String word) {

        HashSet<Synset> synsets = new HashSet<>();

        try {
            for (IndexWord iw : dict.lookupAllIndexWords(word).getIndexWordArray()) {
                //for each matching word in WordNet add all it's senses to the set we are building up
                synsets.addAll(Arrays.asList(iw.getSenses()));
            }
        } catch (JWNLException e) {
            throw new RuntimeException(e);
        }

        //we have finished so return the synsets we found
        return synsets;
    }


    /**
     * @Description: 通过LIN算法获取相似度
     */
    private static Double getLINSimilarityScore(Synset s1, Synset s2) {
        //if the POS tags are not the same then return 0 as this measure
        //only works with 2 nouns or 2 verbs.
        if (!s1.getPOS().equals(s2.getPOS())) return 0.0;

        //Get the Information Content (IC) values for the two supplied synsets
        double ic1 = getIC(s1);
        double ic2 = getIC(s2);

        //Get the Lowest Common Subsumer (LCS) of the two synsets
        Synset lcs = getLCSbyIC(s1, s2);

        //get the IC valueof the LCS
        double icLCS = getIC(lcs);

        //caluclaue the similarity score
        double sim = (2 * icLCS) / (ic1 + ic2);

        //cache and return the calculated similarity
        return sim;
    }


    /**
     * @Description: 通过JCN算法获取相似度
     */
    private static Double getJCNSimilarityScore(Synset s1, Synset s2) {
        //if the POS tags are not the same then return 0 as this measure
        //only works with 2 nouns or 2 verbs.
        if (!s1.getPOS().equals(s2.getPOS())) return 0.0;

        if (!s1.getPOS().equals(POS.NOUN) && !s1.getPOS().equals(POS.VERB)) return 0.0;

        if (!s2.getPOS().equals(POS.NOUN) && !s2.getPOS().equals(POS.VERB)) return 0.0;


        // Get the Information Content (IC) values for the two supplied synsets
        double ic1 = getIC(s1);
        double ic2 = getIC(s2);

        //Get the Lowest Common Subsumer (LCS) of the two synsets
        Synset lcs = getLCSbyIC(s1, s2);
        //get the IC valueof the LCS
        double icLCS = getIC(lcs);

        //compute the distance between the two synset
        double distance = ic1 + ic2 - (2 * icLCS);

        //assume the similarity between the synsets is 0
        double sim = 0;

        if (distance == 0) {
            double rootFreq = getFrequency(s1.getPOS());
            if (rootFreq > 0.01) {
                //if the root frequency has a value then use it to generate a very large sim value
                sim = 1 / -Math.log((rootFreq - 0.01) / rootFreq);
            }
        } else {
            //this is the normal case so just convert the distance to a similarity by taking the multiplicative inverse
            sim = 1 / distance;
        }
        return sim;
    }

    /**
     * @Description: Finds the lowerst common subsumer of the two synsets using information content.
     */
    private static Synset getLCSbyIC(Synset s1, Synset s2) {
        @SuppressWarnings("unchecked") List<List<PointerTargetNode>> trees1 = null;
        List<List<PointerTargetNode>> trees2 = null;
        try {
            trees1 = PointerUtils.getInstance().getHypernymTree(s1).toList();
            trees2 = PointerUtils.getInstance().getHypernymTree(s2).toList();
        } catch (JWNLException e) {
            throw new RuntimeException(e);
        }

        Set<Synset> pLCS = new HashSet<Synset>();

        for (List<PointerTargetNode> t1 : trees1) {
            for (List<PointerTargetNode> t2 : trees2) {
                for (PointerTargetNode node : t1) {
                    if (contains(t2, node.getSynset())) {
                        pLCS.add(node.getSynset());
                        break;
                    }
                }

                for (PointerTargetNode node : t2) {
                    if (contains(t1, node.getSynset())) {
                        pLCS.add(node.getSynset());
                        break;
                    }
                }
            }
        }

        Synset lcs = null;
        double score = 0;

        for (Synset s : pLCS) {
            if (lcs == null) {
                lcs = s;
                score = getIC(s);
            } else {
                double ic = getIC(s);
                if (ic > score) {
                    score = ic;
                    lcs = s;
                }
            }
        }
        if (lcs == null) {
            //link the two synsets by a fake root node
            lcs = new Synset(s1.getPOS(), 0l, new Word[0], new Pointer[0], "", new java.util.BitSet());
        }
        return lcs;
    }


    /**
     * @Description: 获取信息量
     */
    private static Double getIC(Synset synset) {
        //get the POS tag of this synset
        POS pos = synset.getPOS();

        //Information Content is only defined for nouns and verbs
        //so return 0 if the POS tag is something else
        if (!pos.equals(POS.NOUN) && !pos.equals(POS.VERB)) return 0.0;

        //Get the frequency of this synset from the storred data
        Double synFreq = freq.get(getFreqKey(synset));

        //if the frequency isn't defined or it's 0 then simlpy return 0
        if (synFreq == null || synFreq.doubleValue() == 0) return 0.0;

        //Get the frequency of the root node for this POS tage
        Double rootFreq = freq.get(synset.getPOS().getKey());

        //calcualte the probability for this synset
        double prob = synFreq.doubleValue() / rootFreq.doubleValue();

        //if the probability is valid then use it to return the IC value
        if (prob > 0) return -Math.log(prob);

        //something went wrong so assume IC of 0
        return 0.0;
    }

    /**
     * @Description: 获取同义词的Key值
     */
    private static String getFreqKey(Synset synset) {
        return synset.getOffset() + synset.getPOS().getKey();
    }


    private static boolean contains(List<PointerTargetNode> l, Synset s) {
        for (PointerTargetNode node : l) {
            if (node.getSynset().equals(s)) return true;
        }
        return false;
    }

    private static double getFrequency(POS pos) {
        return freq.get(pos.getKey());
    }


}
