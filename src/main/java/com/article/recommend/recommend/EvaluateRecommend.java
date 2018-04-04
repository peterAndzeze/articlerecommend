package com.article.recommend.recommend;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.entity.EvaluateValueInfo;
import com.article.recommend.service.dictionary.DictionaryService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EvaluateRecommend {
    @Autowired
    private DictionaryService dictionaryService;
    /**
     * 计算推荐结果
     * @param dataFile 数据文件
     */
    public void evaluateRecommend(String dataFile) throws IOException {
        DataModel dataModel=RecommendFactory.buildDateMode(dataFile);

    }

    /**
     * 基于用户的PEARSON 指定距离内N个用户推荐
     * @param dataModel
     */
    private  List<RecommendedItem> userCfPearsonRecommend(DataModel dataModel, Map<String,Double> recommendScore, List<EvaluateValueInfo> evaluateValueInfos) throws TasteException {
       //1.用户距离
        //用户相似度距离计算
       UserSimilarity userSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.PEARSON,dataModel);
       //指定距离最近的N个用户
        DictionaryInfo dictionaryInfo=dictionaryService.getDictionaryByKey(RecommendConstant.NEAREST_NUM);
        double num=(dictionaryInfo.getValue() ==null || dictionaryInfo.getValue().equals(""))? 10.0:Double.valueOf(dictionaryInfo.getValue());
        UserNeighborhood userNeighborhood= RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,userSimilarity,dataModel,num);
        RecommenderBuilder recommenderBuilder=RecommendFactory.userRecommender(userSimilarity,userNeighborhood,true);
        //训练数据比例（均方差）//只根据评分
        double score=RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        //多少条数据计算(暂时不用,数据库记录)
        IRStatistics stats= RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        recommendScore.put("userSimilarity_"+RecommendFactory.NEIGHBORHOOD.NEAREST,score);
        EvaluateValueInfo evaluateValueInfo=createEvaluateValueInfo("user",RecommendFactory.SIMILARITY.PEARSON.name(),RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE.name(),score,stats.getPrecision(),stats.getRecall());
        evaluateValueInfos.add(evaluateValueInfo);
        Recommender recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
        return recommenderBuilder.buildRecommender(dataModel).;
    }

    /**
     * 创建对象
     * @param cfType 推荐类型（人或物）
     * @param similarityType （相似度计算方式）
     * @param evaluatorType 评分类型
     * @param score 得分
     * @param precision 查准率
     * @param recall 查全率
     * @return 对象
     */
   private EvaluateValueInfo createEvaluateValueInfo(String cfType,String similarityType,String evaluatorType,Double score,Double precision,Double recall){
       EvaluateValueInfo evaluateValueInfo=new EvaluateValueInfo();
       evaluateValueInfo.setCfType(cfType);
       evaluateValueInfo.setSimilarityType(similarityType);
       evaluateValueInfo.setEvaluatorType(evaluatorType);
       evaluateValueInfo.setPrecision(precision);
       evaluateValueInfo.setRecall(recall);
       evaluateValueInfo.setScore(score);
       evaluateValueInfo.setCreateTime(DateUtil.dateToString(new Date(),DateUtil.DATETIME));
       return evaluateValueInfo;

   }

}
