package com.article.recommend.hadoop.service;

import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.hadoop.bean.ArticleBean;
import com.article.recommend.hadoop.util.HadoopUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * 数据服务（sqoop 服务没有拿到）
 */
public class DataService {
    public static class DataAccessMap extends Mapper<LongWritable,ArticleBean,Text,Text>{
        @Override
        protected void map(LongWritable key, ArticleBean value,Context context)
                throws IOException, InterruptedException {
            System.out.println(value.toString());
            context.write(new Text(), new Text(value.toString()));
        }
    }

    public static class DataAccessReducer extends Reducer<Text,Text,Text,Text> {
        protected void reduce(Text key, Iterable<Text> values,
                              Context context)
                throws IOException, InterruptedException {
            for(Iterator<Text> itr = values.iterator(); itr.hasNext();)
            {
                context.write(key, itr.next());
            }
        }
    }

    /**
     * 执行倒入语句
     * @param className
     * @param fields
     * @param table
     * @param sql
     */
    public static  void  executeMapducer(Class className,String [] fields,String table,String sql){
        Configuration configuration= HadoopUtil.createHadoopConf();
        DBConfiguration.configureDB(configuration,"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/information?useSSL=true&useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true", "root", "admin");
       try {
           Job job =Job.getInstance(configuration);
           job.setJarByClass(DataService.class);

           job.setMapperClass(DataAccessMap.class);
           job.setReducerClass(DataAccessReducer.class);
/*
           job.setMapOutputKeyClass(Text.class);           //*//*
           job.setMapOutputValueClass(Text.class);  //*/
           job.setOutputKeyClass(Text.class);
           job.setOutputValueClass(Text.class);     //
           job.waitForCompletion(true);

           String outputDir = RecommendConstant.BASEPATH + File.separatorChar + RecommendConstant.ARTICLE_PATH+"/tmp";
           FileOutputFormat.setOutputPath(job, new Path(outputDir));
           //对应数据库中的列名(实体类字段)

           DBInputFormat.setInput(job, className, sql,"select count(1) from tb_article");
           System.exit(job.waitForCompletion(true) ? 0 : 1);
       }catch (Exception e){
           System.out.println("导出数据异常"+e.getMessage());
           e.printStackTrace();
       }
    }
    public static  void main(String [] args){
        Field [] fields= ArticleBean.class.getFields();
        String [] strFields=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            strFields[i]=String.valueOf(fields[i]);
        }
        executeMapducer(ArticleBean.class,strFields,"tb_article","select * from tb_article where id between 1 and 10 ");
    }
}
