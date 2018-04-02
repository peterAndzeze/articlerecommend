package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;

/**
 * 执行推荐
 */
public class ExecuteRecommend {
    public static void main(String[] args) throws IOException, TasteException {
        DataModel dataModel=new GroupLensDataModel(new File("/home/newcapec/workspace/articlerecommend/src/main/data/ml-1m/ratings.dat"));
        UserSimilarity userSimilarity=new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(100,userSimilarity,dataModel);
        Recommender recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
        LoadEvaluator.runLoad(recommender);

    }

}
