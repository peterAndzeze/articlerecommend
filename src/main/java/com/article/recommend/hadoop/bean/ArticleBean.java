package com.article.recommend.hadoop.bean;

import com.article.recommend.Util.DateUtil;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 文章对应的hdfs 操作
 */
public class ArticleBean implements Writable, DBWritable {
    private Long id;//虚拟主键
    private String  content;//文章内容（标签信息）
    private Date release_time;//发布时间
    private Long topic_id;//主题
    private String source_url;//文章来源
    private String title;//文章标题
    private String article_lables;//



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

    public Date getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getArticle_lables() {
        return article_lables;
    }

    public void setArticle_lables(String article_lables) {
        this.article_lables = article_lables;
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
        this.id=resultSet.getLong("id");
        this.content=resultSet.getString("content");
        this.release_time=resultSet.getTime("release_time");
        this.topic_id=resultSet.getLong("topic_id");
        this.source_url=resultSet.getString("source_url");//文章来源
        this.title=resultSet.getString("title");//文章标题
        this.article_lables=resultSet.getString("article_lables");//
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.id);
        dataOutput.writeUTF(this.source_url);
        dataOutput.writeUTF(this.title);
        dataOutput.writeUTF(this.article_lables);
        dataOutput.writeLong(this.topic_id);
        dataOutput.writeUTF(DateUtil.dateToString(this.release_time,DateUtil.DATETIME));
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readLong();
        this.article_lables=dataInput.readUTF();
        this.content=dataInput.readUTF();
        this.source_url=dataInput.readUTF();
        this.title=dataInput.readUTF();
        this.topic_id=dataInput.readLong();
        this.release_time=DateUtil.stringToDate(dataInput.readUTF(),DateUtil.DATETIME);
    }

    @Override
    public String toString() {
        return "->"+content  + "->" + release_time + "->" + topic_id + "->" + source_url +"->" + title +"->" + article_lables;

    }
}
