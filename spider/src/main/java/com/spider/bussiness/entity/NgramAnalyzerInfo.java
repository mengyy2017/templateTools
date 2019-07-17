package com.spider.bussiness.entity;

import com.common.pub.pubInter.AnalyzerAnnotation;

public class NgramAnalyzerInfo {

    private AnalyzerAnnotation.AnalyzerName analyzerName;

    private String tokenizerName;

    private String ngramType;

    private int minGram = 2;

    private int maxGram = 4;

    public AnalyzerAnnotation.AnalyzerName getAnalyzerName() {
        return analyzerName;
    }

    public void setAnalyzerName(AnalyzerAnnotation.AnalyzerName analyzerName) {
        this.analyzerName = analyzerName;
    }

    public String getTokenizerName() {
        return tokenizerName;
    }

    public void setTokenizerName(String tokenizerName) {
        this.tokenizerName = tokenizerName;
    }

    public String getNgramType() {
        return ngramType;
    }

    public void setNgramType(String ngramType) {
        this.ngramType = ngramType;
    }

    public int getMinGram() {
        return minGram;
    }

    public void setMinGram(int minGram) {
        this.minGram = minGram;
    }

    public int getMaxGram() {
        return maxGram;
    }

    public void setMaxGram(int maxGram) {
        this.maxGram = maxGram;
    }
}
