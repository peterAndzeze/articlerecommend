package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;

/**
 * 训练数据 评估结果
 */
public class EvaluateValue {

    public static void main(String[] args) throws IOException {
        //recommenderIRStatsEvaluator();
        evaluateValue();
    }

    /**
     * 查准率和和查全率
     */
    public static  void recommenderIRStatsEvaluator() throws IOException {
        RandomUtils.useTestSeed();
        DataModel dataModel=getDataModel();
        RecommenderIRStatsEvaluator recommenderIRStatsEvaluator=new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder=new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity userSimilarity=new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(2,userSimilarity,dataModel);
                return new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            }
        };
        //推荐两个结果时的查准率和查全率
        try {
            IRStatistics irStatistics=recommenderIRStatsEvaluator.evaluate(recommenderBuilder,null,dataModel,null,2,GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);
            System.out.println("推荐两个结果的查准率:"+irStatistics.getPrecision());
            System.out.println("推荐两个结果的查全率:"+irStatistics.getRecall());
        } catch (TasteException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @throws IOException
     */
    public static void evaluateValue() throws  IOException{
        //评估推荐结果
        //RandomUtils.useTestSeed();实际代码中拒绝使用，每次会选择同样的随机值
        DataModel dataModel=getDataModel();
        RecommenderEvaluator evaluator=new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder=new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                System.out.println("数据集："+dataModel.toString());
                UserSimilarity userSimilarity=new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(2,userSimilarity,dataModel);

                return  new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            }
        };
        //训练70%的数据，测试30%
        double score= 0;
        try {
            score = evaluator.evaluate(recommenderBuilder,null,dataModel,0.7,1.0);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        System.out.println("评估结果:"+score);
    }

    /**
     * 获取数据
     * @return
     * @throws IOException
     */
    public static DataModel getDataModel() throws IOException {
        DataModel dataModel=new FileDataModel(new File("/home/newcapec/workspace/articlerecommend/src/main/data/ml-100k/ua.base"));
        return dataModel;
    }

}
