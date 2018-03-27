package com.article.recommend.localservice;

import com.alibaba.fastjson.JSON;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.entity.ExecuteDbRecord;
import com.article.recommend.mapper.localMapper.DictionaryMapper;
import com.article.recommend.service.executedbservice.ExecuteDbService;
import com.article.recommend.service.history.HistoryDataService;
import com.article.recommend.service.importdataservice.ImportDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalServiceTest {
    @Autowired
    ExecuteDbService executeDbService;
    @Autowired
    ImportDataService importDataService;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private HistoryDataService historyDataService;
    @Test
    public void getExecuteDbRecorde(){
        ExecuteDbRecord executeDbRecord=executeDbService.getExecuteDbRecord("0");
        System.out.println(JSON.toJSONString(executeDbRecord)+"**************");
    }
    @Test
    public void impitArticles(){
        importDataService.importArticleData();
    }
    @Test
    public void getDictionary(){
        DictionaryInfo dictionaryInfo=dictionaryMapper.getDictionaryByKey(RecommendConstant.ARTICLE_LOSE_TIME);
        System.out.println(dictionaryInfo.getDisplay()+":"+dictionaryInfo.getValue());
    }
    @Test
    public void deleteLoseArticle(){

        historyDataService.executeArticles();
    }

}

