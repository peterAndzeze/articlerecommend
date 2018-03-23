package com.article.recommend.hadoop.bean;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.entity.ArticleInfo;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ArticleBean implements DBWritable {
    private Long id;//虚拟主键
    private String  content;//文章内容（标签信息）
    private Date releaseTime;//发布时间
    private Long topicId;//主题
    private String sourceUrl;//文章来源
    private String title;//文章标题
    private String articleLables;//

    public String getArticleLables() {
        return articleLables;
    }

    public void setArticleLables(String articleLables) {
        this.articleLables = articleLables;
    }

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

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
       // preparedStatement.setInt(1,1);
       // preparedStatement.setInt(2,10);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        id=resultSet.getLong("id");
        content=resultSet.getString("content");
        releaseTime=resultSet.getTime("release_time");
        topicId=resultSet.getLong("topic_id");
        sourceUrl=resultSet.getString("source_url");//文章来源
        title=resultSet.getString("title");//文章标题
        articleLables=resultSet.getString("article_lables");//
    }


}
