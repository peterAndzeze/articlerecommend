package com.article.recommend.hadoop.service;

import com.article.recommend.hadoop.util.HadoopUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;


/**
 * hadoop文件操作
 */
public class HdfsService {
    private static final  String HDFSBASEPATH="hdfs://hadoop2.campus-card.com:8020/opt";

    /**
     *导入用户数据
     */
    public void importUserData(){

    }

    public void getPath(){
        Configuration configuration= HadoopUtil.createHadoopConf();
        FileSystem fileSystem= HadoopUtil.createFileSystem(configuration);
        System.out.println(fileSystem.getHomeDirectory());

        try {
            FileStatus[] files=fileSystem.listStatus(new Path(HDFSBASEPATH));
            for(int i=0;i<files.length;i++){
                System.out.println(files[i].getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String [] args ){
        new HdfsService().getPath();
    }
}
