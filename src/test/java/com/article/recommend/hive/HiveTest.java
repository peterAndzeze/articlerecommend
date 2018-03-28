/*
package com.article.recommend.hive;

import com.article.recommend.ArticlerecommendApplicationTests;
import com.article.recommend.hadoop.hivedao.HiveDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HiveTest extends ArticlerecommendApplicationTests {
    @Autowired
    HiveDao hiveDao;
    @Test
    public void createTable(){
        String [] clumns={"id","content","release_time","topic_id","source_url","title","article_lables"};
        String [] dataType={"int","String","String","String","String","String","String"};

        hiveDao.createTable("tb_article_info",clumns,dataType,"->");
        hiveDao.showTables();
    }
    @Test
    public void deleteTable(){
        hiveDao.dropTable("tb_article_info");
    }
}

*/
