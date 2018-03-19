package com.article.recommend.entity;

import java.util.Date;

/**
 * Created by sw on 2018/3/18.
 */
public class ArticleInfo {
    private Long id;//虚拟主键
    private String  content;//文章内容（标签信息）
    private Date releaseTime;//发布时间
    private Long topicId;//主题
    private String sourceUrl;//文章来源
    private String title;//文章标题

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}