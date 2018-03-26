package com.article.recommend.hadoop.service;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.dbsourcemanager.PropertiesModel;
import com.article.recommend.entity.ExecuteDbRecord;
import com.article.recommend.hadoop.bean.ArticleBean;
import com.article.recommend.hadoop.util.HadoopUtil;
import com.article.recommend.hadoop.util.HdfsUtil;
import com.article.recommend.service.executedbservice.ExecuteDbService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

/**
 * 文章数据操作
 */
@Service
public class ArticleDataService {
    @Autowired
    private  ExecuteDbService executeDbService;
    @Autowired
    private PropertiesModel propertiesModel;
    public static    class  DBAccessMapper extends MapReduceBase implements Mapper<LongWritable,ArticleBean,LongWritable,Text> {

        @Override
        public void map(LongWritable longWritable, ArticleBean articleBean, OutputCollector<LongWritable, Text> outputCollector, Reporter reporter) throws IOException {
            outputCollector.collect(new LongWritable(articleBean.getId()),new Text(articleBean.toString()) );
        }
    }
    public static   class DBAccessReduce extends   MapReduceBase implements Reducer<LongWritable,Text,LongWritable,Text> {

        @Override
        public void reduce(LongWritable key, Iterator<Text> values, OutputCollector<LongWritable, Text> outputCollector, Reporter reporter) throws IOException {
            while (values.hasNext()){
                outputCollector.collect(key,values.next());
            }
        }
    }

    /**
     * 执行文章数据导入
     */
    public    void executeDataJob() throws IOException {
        Configuration configuration= HadoopUtil.createHadoopConf();
        JobConf jobConf=new JobConf(configuration);
        jobConf.setOutputKeyClass(LongWritable.class);
        jobConf.setOutputValueClass(Text.class);
        jobConf.setInputFormat(DBInputFormat.class);
        String[] fields={"id","content","release_time","topic_id","source_url","title","article_lables"};
        ExecuteDbRecord executeDbRecord=executeDbService.getExecuteDbRecord(RecommendConstant.DB_ARTICLE_TYPE);
        String conditions="id between "+executeDbRecord.getLimitId()+" and 10";
        String businessDate= DateUtil.dateToString(new Date(),DateUtil.DATE);
        DBInputFormat.setInput(jobConf,  ArticleBean.class,"tb_article",conditions,"id",fields);
        //DBConfiguration.configureDB(jobConf,"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/information?useSSL=true&useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true", "root", "admin");
        DBConfiguration.configureDB(jobConf,propertiesModel.getDriverClassName(),propertiesModel.getInfromationHost(),propertiesModel.getInformationUser(),propertiesModel.getInformationPassword());
        jobConf.setMapperClass(DBAccessMapper.class);
        jobConf.setReducerClass(DBAccessReduce.class);
        String outPath= RecommendConstant.BASEPATH+ File.separatorChar+RecommendConstant.ARTICLE_PATH;
        HdfsUtil.deleteDir("tmpData",outPath);
        FileOutputFormat.setOutputPath(jobConf,new Path(outPath+"/tmpData"));
        JobClient.runJob(jobConf);
        //更新记录表
    }

}
