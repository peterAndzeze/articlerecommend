package com.article.recommend.hive;

import com.article.recommend.ArticlerecommendApplicationTests;
import com.article.recommend.hadoop.util.HiveUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HiveTest extends ArticlerecommendApplicationTests {
    @Autowired
    HiveUtil hiveUtil;
    @Test
    public void createTable(){
        String [] clumns={"id","content","release_time","topic_id","source_url","title","article_lables"};
        String [] dataType={"int","String","String","String","String","String","String"};

        //hiveUtil.createTable("tb_article_info",clumns,dataType,"->");
        hiveUtil.showTables();
    }

}

