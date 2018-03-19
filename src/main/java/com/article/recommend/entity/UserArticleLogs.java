package com.article.recommend.entity;

import java.util.Date;

/**
 * Created by sw on 2018/3/18.
 * 用户操作文章结果信息
 */

public class UserArticleLogs {
    private  Long id;
    private Long userId;//用户编号
    private Long article_id;//文章编号
    private Date viewTime;//浏览时间
    private int prefer_degree=0;//用户偏好程度

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public int getPrefer_degree() {
        return prefer_degree;
    }

    public void setPrefer_degree(int prefer_degree) {
        this.prefer_degree = prefer_degree;
    }
}