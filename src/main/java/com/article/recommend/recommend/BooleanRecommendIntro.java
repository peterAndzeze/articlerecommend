package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * 无偏好值，采用布尔型表示（喜欢、不喜欢、无所谓）
 */
public class BooleanRecommendIntro {
    public static void main(String[] args) throws IOException, TasteException {
        //推荐结果并不好，数据在整体结果集中只查询出来24%，准确率也是，这里不讨论优化推荐，而是展示下，没有或者忽略偏好值的情况下一种实现
        booleanPreData();
    }

    public static  void booleanPreData() throws IOException, TasteException {

        //过期了。。。
         DataModel dataModel=new GenericBooleanPrefDataModel(new FileDataModel(new File("/home/newcapec/workspace/articlerecommend/src/main/data/ml-100k/ua.base")));
        RecommenderIRStatsEvaluator evaluator=new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder=new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity userSimilarity=new LogLikelihoodSimilarity(dataModel);
                UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(10,userSimilarity,dataModel);

                return new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            }
        };


        DataModelBuilder dataModelBuilder=new DataModelBuilder() {
            @Override
            public DataModel buildDataModel(FastByIDMap<PreferenceArray> fastByIDMap) {
                return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(fastByIDMap));
            }
        };

        IRStatistics irStatistics=evaluator.evaluate(recommenderBuilder,dataModelBuilder,dataModel,null,10,GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);

        System.out.println("查准率："+irStatistics.getPrecision());
        System.out.println("查全率:"+irStatistics.getRecall());

    }

}
