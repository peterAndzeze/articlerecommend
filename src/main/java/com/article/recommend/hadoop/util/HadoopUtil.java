package com.article.recommend.hadoop.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * hadoop 基类负责hadoop环境的初始化操作
 */
public class HadoopUtil {
    private static final  String HDFSHOME="hdfs://hadoop2.campus-card.com:8020";
    /**
     * 初始化hadoop环境
     * 暂时每次都创建
     * @return
     */
    public static Configuration createHadoopConf(){
        Configuration configuration=new Configuration();
        configuration.addResource(new Path("classpath:/capec/hdfs-site.xml"));
        configuration.addResource(new Path("classpath:/capec/mapred-site.xml"));
        configuration.addResource(new Path("classpath:/capec/core-site.xml"));
        configuration.addResource(new Path("classpath:/capec/yarn-site.xml"));
        configuration.set("fs.defaultFS",HDFSHOME);
        return configuration;
    }

    /**
     * 初始化FileSystem
     * @param configuration hadoop 配置文件
     * @return
     */
    public static FileSystem createFileSystem(Configuration configuration){
        Path path=new Path(HDFSHOME);
        try {
            if(null ==configuration){
                return  FileSystem.get(createHadoopConf());
            }else{
                return FileSystem.get(configuration);
            }
        } catch (IOException e) {
            System.out.println("初始化hadoop环境异常:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
