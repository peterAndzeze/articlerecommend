package com.article.recommend.entity;

/**
 * Created by sw on 2018/3/18.
 * 文章主题信息
 */
public class ArticleTopicInfo {

    private Long id;
    private String topicName;//文章主题信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}