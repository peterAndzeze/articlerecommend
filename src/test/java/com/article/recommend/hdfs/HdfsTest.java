package com.article.recommend.hdfs;

import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.hadoop.util.HdfsUtil;
import org.apache.hadoop.fs.Hdfs;
import org.apache.hadoop.hdfs.client.HdfsUtils;
import org.junit.Test;

import java.io.IOException;

public class HdfsTest {
    @Test
    public  void getChildrens(){

            HdfsUtil.getFiles(RecommendConstant.BASEPATH+"userprefs");

    }
    //创建文件夹
    @Test
    public  void mkdir(){
       // HdfsUtil.deleteDir(RecommendConstant.BASEPATH+"/userprefs/loseData");

        HdfsUtil.mkdir("userprefs/loseData", RecommendConstant.BASEPATH);
        getChildrens();
    }
    //上传测试数据文件
    @Test
    public  void uploadFile() throws IOException {
       // HdfsUtil.uploadHdfsFile("/home/newcapec/桌面/data/2018-04-03.txt",RecommendConstant.BASEPATH+"userprefs/data");
        HdfsUtil.getFiles(RecommendConstant.BASEPATH+"userprefs/data");
    }

    //合并文件
    @Test
    public  void  mergeFile() throws IOException {
      String filesPath= RecommendConstant.BASEPATH+"userprefs/data";
      String newFile=RecommendConstant.BASEPATH+"userprefs/tmp.txt";
     HdfsUtil.copyMerge(filesPath,newFile);

      String content= HdfsUtil.readHdfsFile(RecommendConstant.BASEPATH+"userprefs/tmp.txt");System.out.println(content);
    }

}
