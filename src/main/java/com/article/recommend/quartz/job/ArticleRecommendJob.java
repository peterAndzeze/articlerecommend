package com.article.recommend.quartz.job;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.hadoop.util.HdfsUtil;
import com.article.recommend.recommend.RecommendFactory;
import com.article.recommend.recommend.UserRecommend;
import org.apache.hadoop.fs.FileStatus;
import org.apache.mahout.cf.taste.model.DataModel;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * 定时任务执行推荐job
 */
@Service
public class ArticleRecommendJob //implements BaseJob
         {
    private static Logger logger= LoggerFactory.getLogger(ArticleRecommendJob.class);
    static  String path= RecommendConstant.BASEPATH+RecommendConstant.USERPREFS_PATH;
    static  String dataPath=path+File.separatorChar+RecommendConstant.USERPREFS_DATA_PATH;
    static  String movePath=path+File.separatorChar+RecommendConstant.USERPREFS_LOSEDATA_PATH;
    static  String tmp=path+File.separatorChar+"tmp.txt";
    @Autowired
    private UserRecommend evaluateRecommend;

     //@Override
    public void execute(JobExecutionContext jobExecutionContext)  {
        //用户行为数据
        String path= RecommendConstant.BASEPATH+ File.separatorChar+RecommendConstant.USERPREFS_PATH;
        //1.删除过期数据
        try {
            deleteLoseData(path);
            //2.合并文件 tmp下
             HdfsUtil.copyMerge(dataPath,tmp);
            //3.下载下来,执行推荐操作
             String local= null;
             local = "data/tmp/tmp.txt";
             File file=new File(local);
             if(file.exists()) {
                 file.delete();
             }
            HdfsUtil.downFile(tmp, local);
            evaluateRecommend.evaluateRecommend(local);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /**
     * 删除目录下无效的行为数据（转移到别的地方）
     * @param path
     */
    public void deleteLoseData(String path) throws IOException, ParseException {

        logger.info("行为数据路径：{}",dataPath);
        FileStatus[] fileStatuse= HdfsUtil.getFiles(dataPath);
        String businessDate= DateUtil.getBeforeDate(30,DateUtil.DATE);
        logger.info("有效行为数据的截止日期:{}",businessDate);
        String dateName=null;
        String fileName=null;
        for(int i=0;i<fileStatuse.length;i++){
            fileName=fileStatuse[i].getPath().getName();
            dateName =fileName.substring(0,fileName.lastIndexOf("."));
            int compareResult=DateUtil.compareDateStr(dateName,businessDate);
            if(compareResult<1){//满足条件，将数据转移到失效文件夹下
                   HdfsUtil.moveFile(dataPath+"/"+fileName,movePath+"/"+fileName);
            }
        }
    }

    /**
     * 数据文件
     * @param dataFile
     */
    protected  void executeRecommend(String dataFile) throws IOException {
        DataModel dataModel= RecommendFactory.buildDateModel(dataFile);

    }

    public static void main(String[] args) {
        String businessDate= DateUtil.getBeforeDate(30,DateUtil.DATE);
        System.out.println("业务日期:"+businessDate);
        try {
           /* new ArticleRecommendJob().deleteLoseData(path);
            String dataPath=path+File.separatorChar+RecommendConstant.USERPREFS_DATA_PATH;
            String movePath=path+File.separatorChar+RecommendConstant.USERPREFS_LOSEDATA_PATH;
            HdfsUtil.getFiles(dataPath);
            HdfsUtil.getFiles(movePath);
            HdfsUtil.copyMerge(dataPath,tmp);*/
           /* String dataPath=path+File.separatorChar+RecommendConstant.USERPREFS_DATA_PATH;
            HdfsUtil.copyMerge(dataPath,tmp);*/


        } catch (Exception e) {
           e.printStackTrace();
        }

    }

}
