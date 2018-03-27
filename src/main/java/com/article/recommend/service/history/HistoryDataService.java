package com.article.recommend.service.history;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.mapper.localMapper.DictionaryMapper;
import com.article.recommend.mapper.localMapper.LocalArticleDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 历史数据处理
 */
@Service
public class HistoryDataService {
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private LocalArticleDataMapper localArticleDataMapper;
    /**
     * 从正式表中剔除历史文章数据
     */
    @Transactional(value = "localDataTransactionManager",transactionManager = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public void executeArticles(){
        DictionaryInfo dictionaryInfo=dictionaryMapper.getDictionaryByKey(RecommendConstant.ARTICLE_LOSE_TIME);
        //获取失效天数
        int loseDays=Integer.valueOf(dictionaryInfo.getValue());
        String businessDate= DateUtil.getBeforeDate(6,DateUtil.DATE);
        System.out.println("删除数据日期:"+businessDate);
        //插入历史数据
        localArticleDataMapper.insertLoseArticleData(businessDate);
        localArticleDataMapper.deleteLoseArticleData(businessDate);
    }

}
