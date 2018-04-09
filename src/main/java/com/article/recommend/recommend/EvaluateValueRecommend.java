package com.article.recommend.recommend;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.entity.EvaluateValueInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class EvaluateValueRecommend {
    private static Logger logger= LoggerFactory.getLogger(EvaluateValueRecommend.class);
    /**
     * 基于用户的PEARSON 指定距离内N个用户推荐
     * @param dataModel
     */
    private Recommender userRecommend(DataModel dataModel , int nearNum, int topNum, Double trainPt) throws TasteException {
        //DictionaryInfo topDictionary=dictionaryService.getDictionaryByKey(RecommendConstant.TOP_NUM);

        //int topn=topDictionary.getValue()==null?2:Integer.valueOf(topDictionary.getValue());
       // DictionaryInfo trainPtDic=dictionaryService.getDictionaryByKey(RecommendConstant.TRAINPT_NUM);
        //Double trainPt=trainPtDic.getValue()==null?0.9:Double.valueOf(trainPtDic.getValue());
        List<EvaluateValueInfo> evaluateValueInfos=new ArrayList<>();

        //1.用户距离
        //用户相似度距离计算
        //皮尔逊
        logger.info("基于用户计算start");
        UserSimilarity userSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.PEARSON,dataModel);
        UserNeighborhood userNeighborhood= RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,userSimilarity,dataModel,nearNum);

        evaludateValue(userSimilarity,userNeighborhood,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.PEARSON.name());
        logger.info("基于用户计算皮尔逊end");

        //cosin
       /* UserSimilarity cosinSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.COSINE,dataModel);
        UserNeighborhood consinNeighborhood= RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,cosinSimilarity,dataModel,nearNum);
        evaludateValue(cosinSimilarity,consinNeighborhood,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.COSINE.name());
        logger.info("基于用户计算cosinend");

        //TANIMOTO
        UserSimilarity tanimotoSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.TANIMOTO,dataModel);
        UserNeighborhood tanimotoNeigh=RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,tanimotoSimilarity,dataModel,nearNum);
        evaludateValue(tanimotoSimilarity,tanimotoNeigh,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.TANIMOTO.name());
        logger.info("基于用户计算TANIMOTO end");

        //LOGLIKELIHOOD 对数似然距离

        UserSimilarity loglikelihoodSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.LOGLIKELIHOOD,dataModel);
        UserNeighborhood loglikelihoodNeigh=RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,loglikelihoodSimilarity,dataModel,nearNum);
        evaludateValue(loglikelihoodSimilarity,loglikelihoodNeigh,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.LOGLIKELIHOOD.name());
        logger.info("基于用户计算LOGLIKELIHOOD end");

        //CITYBLOCK 曼哈顿距离

        UserSimilarity cityblockSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.CITYBLOCK,dataModel);
        UserNeighborhood cityblockNeigh=RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,loglikelihoodSimilarity,dataModel,nearNum);
        evaludateValue(cityblockSimilarity,cityblockNeigh,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.CITYBLOCK.name());
        logger.info("基于用户计算CITYBLOCK end");

        //EUCLIDEAN 欧式距离
       UserSimilarity euclideanSimilarity=RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.EUCLIDEAN,dataModel);
        UserNeighborhood euclideanNeigh=RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST,euclideanSimilarity,dataModel,nearNum);
        evaludateValue(euclideanSimilarity,euclideanNeigh,dataModel,topNum,trainPt,evaluateValueInfos,RecommendFactory.SIMILARITY.EUCLIDEAN.name());
        logger.info("基于用户计算EUCLIDEAN end");*/

        //计算最好的
        EvaluateValueInfo evaluateValueInfo=evaluateMaxValue(evaluateValueInfos);
        //evaluateValueInfo.getCfType()
        Recommender recommender=null;
        String similarityType=evaluateValueInfo.getSimilarityType();
        logger.info("基于用户的最好的推荐方式{},评分{}",similarityType,evaluateValueInfo.getScore());
        switch (similarityType){ case "PEARSON":
           /*   recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            case "COSINE" :
                recommender=new GenericUserBasedRecommender(dataModel,consinNeighborhood,cosinSimilarity);
            case "TANIMOTO":
                recommender=new GenericUserBasedRecommender(dataModel,tanimotoNeigh,tanimotoSimilarity);
            case "LOGLIKELIHOOD":
                recommender=new GenericUserBasedRecommender(dataModel,loglikelihoodNeigh,loglikelihoodSimilarity);
            case "CITYBLOCK":
                recommender=new GenericUserBasedRecommender(dataModel,cityblockNeigh,cityblockSimilarity);
            case "EUCLIDEAN":
                recommender=new GenericUserBasedRecommender(dataModel,euclideanNeigh,userSimilarity);*/
            default:
                recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
        }
        //入库推荐计算的评分
        return recommender;
    }

    //指定个数
    public void evaludateValue(UserSimilarity userSimilarity,UserNeighborhood userNeighborhood,DataModel dataModel,int topNum,Double trainPt,List<EvaluateValueInfo> evaluateValueInfos,String similarityType) throws TasteException {
        RecommenderBuilder recommenderBuilder=RecommendFactory.userRecommender(userSimilarity,userNeighborhood,true);
        logger.info("asdasd");
        //训练数据比例（均方差）//只根据评分
        double score=RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, trainPt);
        IRStatistics stats= RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, topNum);
        EvaluateValueInfo evaluateValueInfo=createEvaluateValueInfo("userSimilarity_"+RecommendFactory.NEIGHBORHOOD.NEAREST,similarityType,RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE.name(),score,0.0,0.0);
        evaluateValueInfos.add(evaluateValueInfo);
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

    /**
     * 计算最好，score越低，评分越低意味着估计值与实际实际偏好值得差别越小 0.0 完美
     * @param evaluateValueInfos
     * @return
     */
    public  EvaluateValueInfo evaluateMaxValue(List<EvaluateValueInfo> evaluateValueInfos){

        Collections.sort(evaluateValueInfos, new Comparator<EvaluateValueInfo>() {

            public int compare(EvaluateValueInfo h1, EvaluateValueInfo h2) {
                if(h1.getScore().isNaN()){
                    h1.setScore(0.0);
                }
                if (h2.getScore().isNaN()){
                    h2.setScore(0.0);
                }
                return h1.getScore().compareTo(h2.getScore());
            }
        });
        return evaluateValueInfos.get(0);
    }

    public static void main(String[] args) throws TasteException, IOException {

        String  local="data/book/rating.csv";
        DataModel dataModel=RecommendFactory.buildDateModel(local);

        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
        UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity, dataModel, 2);
        RecommenderBuilder recommenderBuilder = RecommendFactory.userRecommender(userSimilarity, userNeighborhood, true);

        //RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        logger.info("基于用户计算EUCLIDEAN end");
    }
}
