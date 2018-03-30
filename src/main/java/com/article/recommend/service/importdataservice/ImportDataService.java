package com.article.recommend.service.importdataservice;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.entity.ExecuteDbRecord;
import com.article.recommend.mapper.informationmapper.ArticleMapper;
import com.article.recommend.mapper.localMapper.ExecutorDbRecordMapper;
import com.article.recommend.mapper.localMapper.LocalArticleDataMapper;
import com.article.recommend.vo.DataVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ImportDataService {
    
    private static  Logger logger= LoggerFactory.getLogger(ImportDataService.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ExecutorDbRecordMapper executorDbRecordMapper;
    @Autowired
    private LocalArticleDataMapper localArticleDataMapper;
    /**
     * 从资讯平台导入数据到mysql
     */
    @Transactional(value = "localDataTransactionManager",transactionManager = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public void importArticleData(){
        ExecuteDbRecord executeDbRecord=executorDbRecordMapper.getExecuteDbRecord(RecommendConstant.DB_ARTICLE_TYPE);
        String businessDate= DateUtil.dateToString(new Date(),DateUtil.DATE);
        /**查询当前有多少数据*/

        //查询当前有多少数据
        Map<String,Object> params=new HashMap<>();
        params.put("beforeId",executeDbRecord.getBeforeId());
        params.put("businessDate","2018-03-22");
        DataVo dataVo=articleMapper.getCount(params);
        if(dataVo.getMaxId()==0){//不存在最大的id 不存在新数据
           logger.info("没有文章数据导入");
            return ;
        }
        Integer count=dataVo.getCount();//文章最大数
       logger.info("文章数据:"+count+",每次执行大小："+executeDbRecord.getRownums());
        int rowNums=executeDbRecord.getRownums();
        int pages= pages=(count+rowNums-1)/rowNums;;
       logger.info("执行批次数："+pages);
        Long beforeId=executeDbRecord.getBeforeId();//这一次id抽取最小值
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("beforeId",beforeId);
        queryParams.put("endId",dataVo.getMaxId());
       logger.info("start"+System.currentTimeMillis());
        List<ArticleInfo> allList=new ArrayList<>();
        for (int i=1;i<=pages;i++){
            queryParams.put("from",rowNums*(i-1));
            queryParams.put("end",rowNums);
           //logger.info("from:"+rowNums*(i-1)+",to："+rowNums);
            List<ArticleInfo> articleInfos=articleMapper.queryArticles(queryParams);
            localArticleDataMapper.insertLocalArticle(articleInfos);
        }
        //更新记录表
        executeDbRecord.setBeforeId(dataVo.getMaxId());
        executeDbRecord.setUpdateTime(DateUtil.dateToString(new Date(),DateUtil.DATETIME));
        executorDbRecordMapper.updateExecuteDbRecord(executeDbRecord);
       logger.info("end:"+System.currentTimeMillis());

    }

    public void testMethod(){
       logger.info("job注入spring注解类的方法执行");
    }



}
