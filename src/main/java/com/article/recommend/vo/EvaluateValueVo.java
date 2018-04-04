package com.article.recommend.vo;

/**
 * 推荐结果评分和查准、查全比例
 */
public class EvaluateValueVo {
    //距离计算类型
    private String similarityType;
    //用户距离类型
    private String userNeighborhood;


    //评分
    private Double score;
    //查准率
    private Double precision;
    //查全率
    private Double recall;

    public String getSimilarityType() {
        return similarityType;
    }

    public void setSimilarityType(String similarityType) {
        this.similarityType = similarityType;
    }

    public String getUserNeighborhood() {
        return userNeighborhood;
    }

    public void setUserNeighborhood(String userNeighborhood) {
        this.userNeighborhood = userNeighborhood;
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
}
