package com.article.recommend.hadoop.service;

import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.hadoop.util.HadoopUtil;
import com.article.recommend.hadoop.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;


/**
 * hadoop文件操作
 */
public class HdfsService {


    /**
     *导入用户数据
     */
    public void importUserData(){

    }



    public void getPath(){
        Configuration configuration= HadoopUtil.createHadoopConf();
        FileSystem fileSystem= HadoopUtil.createFileSystem(configuration);
        //System.out.println(fileSystem.getHomeDirectory());

        try {
            FileStatus[] files=fileSystem.listStatus(new Path(RecommendConstant.BASEPATH+RecommendConstant.ARTICLE_PATH+File.separatorChar+"tmpData"));
            for(int i=0;i<files.length;i++){
                System.out.println("根目录下文件夹："+files[i].getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String [] args ){
        //boolean flag=HdfsUtil.mkdir("userinfo",RecommendConstant.BASEPATH);
        //boolean flag=HdfsUtil.deleteDir(RecommendConstant.USERINFO_PATH,RecommendConstant.BASEPATH);
        //System.out.println("删除文件夹结果："+flag+"****");
        //new HdfsService().getPath();
        //boolean result=HdfsUtil.mkdir(RecommendConstant.USERINFO_PATH,RecommendConstant.BASEPATH);
        //System.out.println("创建文件夹结果："+flag+"****");

        //new HdfsService().getPath();
        //boolean fileResult=HdfsUtil.createHdfsFile("user.txt",RecommendConstant.BASEPATH+RecommendConstant.USERINFO_PATH,"hello world");
        //System.out.println("创建文件结果:"+fileResult);
/*        String readInfo=HdfsUtil.readHdfsFile(RecommendConstant.BASEPATH+RecommendConstant.ARTICLE_PATH+File.separatorChar+"part-r-00000");
        System.out.println("读取的内容:"+readInfo);*/
        //boolean flag=HdfsUtil.appendContenToHdfsFile(RecommendConstant.BASEPATH+RecommendConstant.USERINFO_PATH+File.separatorChar+"user.txt","您好");
        //HdfsUtil.appendFileToHdfsFile("/home/newcapec/app/atom/mahout/系统模块",RecommendConstant.BASEPATH+RecommendConstant.USERINFO_PATH+File.separatorChar+"user.txt");
        String readInfo=HdfsUtil.readHdfsFile(RecommendConstant.BASEPATH+RecommendConstant.ARTICLE_PATH+File.separatorChar+"tmpData/part-00000");
        System.out.println("读取的内容:"+readInfo);
       // HdfsUtil.delHdfs(RecommendConstant.BASEPATH+RecommendConstant.ARTICLE_PATH+File.separatorChar+"tmp");

       // HdfsUtil.deleteDir("artileinfo",RecommendConstant.BASEPATH);

        //new HdfsService().getPath();
    }
}
