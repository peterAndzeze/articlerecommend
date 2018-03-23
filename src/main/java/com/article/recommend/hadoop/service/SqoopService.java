package com.article.recommend.hadoop.service;

import com.article.recommend.constant.RecommendConstant;
import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MLinkConfig;
import org.apache.sqoop.validation.Status;

/**
 * sqoop 服务。操作mysql数据库和hdfs 数据
 */
public class SqoopService {
    /**
     * 初始化sqoop环境
     */
    public static   void initSqoopEnv(){
        SqoopClient sqoopClient=new SqoopClient(RecommendConstant.HDFSBASEPATH);
        MLink link=sqoopClient.createLink("db");
        MLinkConfig linkConfig = link.getConnectorLinkConfig();
        // fill in the link config values
        linkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://localhost/my");
        linkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
        linkConfig.getStringInput("linkConfig.username").setValue("root");
        linkConfig.getStringInput("linkConfig.password").setValue("root");
        // save the link object that was filled
        Status status = sqoopClient.saveLink(link);
        if(status.canProceed()) {
            System.out.println("Created Link with Link Name : " + link.getName());
        } else {
            System.out.println("Something went wrong creating the link");
        }
    }
    public static  void main(String [] args){
        initSqoopEnv();
    }
}
