package com.spider.bussiness.entity;

public class NgramInfo {

    private String analyzerName;

    private String tokenizerName;

    private String ngramName;

    private String type;

    private int minGram;

    private int maxGram;

    public String getAnalyzerName() {
        return analyzerName;
    }

    public void setAnalyzerName(String analyzerName) {
        this.analyzerName = analyzerName;
    }

    public String getTokenizerName() {
        return tokenizerName;
    }

    public void setTokenizerName(String tokenizerName) {
        this.tokenizerName = tokenizerName;
    }

    public String getNgramName() {
        return ngramName;
    }

    public void setNgramName(String ngramName) {
        this.ngramName = ngramName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
