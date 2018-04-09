package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.IOException;

public class UserRecommender {
    /**
     * 用户欧式距离
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userEuclidean(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }

    /**
     * 用户余弦
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userCosin(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.COSINE, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        RecommenderBuilder recommenderBuilder = RecommendFactory.userRecommender(userSimilarity, userNeighborhood, true);
        //暂时在此处扔队列计算
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }

    /**
     * 用户皮而逊
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userPearson(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.PEARSON, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }

    /**
     *
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userTanimoto(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.TANIMOTO, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }

    /**
     *
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userCityblock(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.CITYBLOCK, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }

    /**
     *
     * @param dataModel
     * @param nearNum
     * @return
     * @throws TasteException
     * @throws IOException
     */
    public static Recommender userLoglikelihood(DataModel dataModel, int nearNum) throws TasteException, IOException {
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.LOGLIKELIHOOD, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, nearNum);
        Recommender recommender=RecommendFactory.userRecommender(dataModel,userSimilarity,userNeighborhood,true);
        return recommender;
    }


}
