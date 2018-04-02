package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommenderIntro {
    public static void main(String[] args) {
        try {
            DataModel dataModel=new FileDataModel(new File("/home/newcapec/workspace/articlerecommend/src/main/data/intro.csv"));
            System.out.println(dataModel.getItemIDs());
            UserSimilarity userSimilarity=new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(2,userSimilarity,dataModel);
            //生成推荐
            Recommender recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            //为用户1 生成1个推荐
            List<RecommendedItem> recommendedItems=recommender.recommend(1,1);
            recommendedItems.forEach(e -> System.out.println("为用户1推荐："+e.getItemID()+"，value:"+e.getValue()));


        }catch (IOException e){
            System.out.println("异常:"+e.getMessage());
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}
