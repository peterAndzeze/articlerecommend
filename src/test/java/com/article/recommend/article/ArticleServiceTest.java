package com.article.recommend.article;

import com.alibaba.fastjson.JSON;
import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.mapper.informationmapper.ArticleMapper;
import com.article.recommend.vo.DataVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceTest {

    @Autowired
    private ArticleMapper articleMapper;

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

            System.out.println("waiting start****");
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("waiting end***** ");
        }
    }

/*
    @Test
    public void executeArticleDB(){
        try {
            articleDataService.executeDataJob();
        } catch (IOException e) {
            System.out.println("导入数据失败！");
            e.printStackTrace();
        }
    }

    @Test
    public void hiveData(){
        articleDataService.loadDataInfoHive(RecommendConstant.BASEPATH+RecommendConstant.ARTICLE_PATH+ File.separatorChar+"tmpData/part-00000");
    }
*/
        @Test
        public void queryArticles(){
            /*List<ArticleInfo> articleInfos=articleService.queryArticles("2018-03-22",0,100);
            System.out.println(articleInfos.size());*/
        }
        @Test
        public void getCount(){
            Map<String,Object> params=new HashMap<>();
            params.put("beforeId","390601121");
           DataVo dataVo=articleMapper.getCount(params);
            System.out.println(dataVo.getCount()+"***"+dataVo.getMaxId());
        }


    @Test
    public void getArticles(){
        Map<String,Object> params=new HashMap<>();
        params.put("limitId","0");
        params.put("endId","1");
        params.put("from",0);
        params.put("end",10);
        List<ArticleInfo> articleInfos=articleMapper.queryArticles(params);
        System.out.println(JSON.toJSON(articleInfos));
    }



}
