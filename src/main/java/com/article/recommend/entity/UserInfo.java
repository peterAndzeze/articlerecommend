package com.article.recommend.entity;

import java.util.Date;

/**
 * Created by sw on 2018/3/18.
 */
public class UserInfo {
    private Long id;	//用户编号
    private String prefList;//关键词列表
    private Date latestLogTime;//
    private String name;//用户姓名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefList() {
        return prefList;
    }

    public void setPrefList(String prefList) {
        this.prefList = prefList;
    }

    public Date getLatestLogTime() {
        return latestLogTime;
    }

    public void setLatestLogTime(Date latestLogTime) {
        this.latestLogTime = latestLogTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}