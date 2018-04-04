package com.article.recommend.entity;

/**
 * 推荐结果评分和查准、查全比例
 */
public class EvaluateValueInfo {
    //推荐类型
    private String cfType;
    //相似度类型
    private String similarityType;
    //评分
    private Double score;
    //查准率
    private Double precision;
    //查全率
    private Double recall;
    //计算时间
    private String createTime;
    //评分类型
    private String evaluatorType;

    public String getCfType() {
        return cfType;
    }

    public void setCfType(String cfType) {
        this.cfType = cfType;
    }

    public String getSimilarityType() {
        return similarityType;
    }

    public void setSimilarityType(String similarityType) {
        this.similarityType = similarityType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public Double getRecall() {
        return recall;
    }

    public void setRecall(Double recall) {
        this.recall = recall;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEvaluatorType() {
        return evaluatorType;
    }

    public void setEvaluatorType(String evaluatorType) {
        this.evaluatorType = evaluatorType;
    }
}
