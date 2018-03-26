package com.article.recommend.article;

import com.article.recommend.Util.SpringUtil;
import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.hadoop.service.ArticleDataService;
import com.article.recommend.service.article.ArticleService;
import com.article.recommend.service.executedbservice.ExecuteDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceTest {
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private ArticleService articleService;
    @Test
    public void getArticle(){
        ArticleInfo articleInfo=articleService.getArticleById(5L);
        System.out.println("************"+articleInfo.getContent()+"->"+articleInfo.getTitle());
    }
    @Test
    @Transactional
    @Rollback(false)
    public void insertArticle(){
        List<ArticleInfo> articleInfos=new ArrayList<>();
        for(int k=0 ;k<2 ;k++) {

            Long j = 3906009L;
            ArticleInfo articleInfo=null;

            for (Long i = j; i < j + 100000L; i++) {
                articleInfo = new ArticleInfo();
                articleInfo.setContent("nunmer" + i + ",connent");
                articleInfo.setReleaseTime(new Date());
                articleInfo.setTitle("numnber" + i + "title");
                articleInfo.setSourceUrl("source_url---" + i);
                articleInfo.setTopicId(i);
                articleInfo.setArticleLables(i + "," + (i + 1) + "," + (i - 1));
                articleInfos.add(articleInfo);
            }
            articleService.insertArticles(articleInfos);
            System.out.println("waiting start****");
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("waiting end***** ");
        }
    }

    @Test
    public void executeArticleDB(){
        try {
            articleDataService.executeDataJob();
        } catch (IOException e) {
            System.out.println("导入数据失败！");
            e.printStackTrace();
        }
    }


}
