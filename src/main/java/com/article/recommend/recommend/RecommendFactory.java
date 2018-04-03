package com.article.recommend.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class RecommendFactory {
    private final static Logger logger= LoggerFactory.getLogger(RecommendFactory.class);
    /**
     * 包含用户评分的数据
     * @param file
     * @return
     */
    public static DataModel buildDateMode(String file) throws IOException {
        return new FileDataModel(new File(file));
    }

    /**
     * 不包含用户评分
     * @param file
     * @return
     */
    public static DataModel buildDataModelNoPref(String file) throws IOException, TasteException {
        return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File(file))));
    }

    public static DataModelBuilder buildDataModelNoPrefBuilder() {
        return new DataModelBuilder() {
            @Override
            public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
                return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
            }
        };
    }

    /**
     * 相似度计算方式
     * PEARSON 皮尔逊
     * EUCLIDEAN 欧式
     * COSINE 余弦等。。。
     */
    public enum SIMILARITY {
        PEARSON, EUCLIDEAN, COSINE, TANIMOTO, LOGLIKELIHOOD, SPEARMAN, CITYBLOCK, FARTHEST_NEIGHBOR_CLUSTER, NEAREST_NEIGHBOR_CLUSTER
    }

    /**
     * 基于用户的相似度计算
     * @param similarity 相似度
     * @param dataModel 数据
     * @return
     */
    public static UserSimilarity userSimilarity(SIMILARITY similarity,DataModel dataModel) throws TasteException {
        switch (similarity) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(dataModel);
            case COSINE:
                return new UncenteredCosineSimilarity(dataModel);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(dataModel);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(dataModel);
            case SPEARMAN:
                return new SpearmanCorrelationSimilarity(dataModel);
            case CITYBLOCK:
                return new CityBlockSimilarity(dataModel);
            case EUCLIDEAN:
            default://默认欧式距离
                return new EuclideanDistanceSimilarity(dataModel);
        }
    }

    /**
     * 基于物品的相似度计算
     * @param similarity 相似度类型
     * @param dataModel  数据
     * @return
     */
    public static ItemSimilarity itemSimilarity(SIMILARITY similarity,DataModel dataModel) throws TasteException {
        switch (similarity) {
            case PEARSON:
                //皮尔森距离：PearsonCorrelationSimilarity
                return new PearsonCorrelationSimilarity(dataModel);
            case COSINE:
                //余弦距离：UncenteredCosineSimilarity
                return new UncenteredCosineSimilarity(dataModel);
            case TANIMOTO:
                //谷本相关系数：TanimotoCoefficientSimilarity
                return new TanimotoCoefficientSimilarity(dataModel);
            case LOGLIKELIHOOD:
                //对数似然距离： LogLikelihoodSimilarity
                return new LogLikelihoodSimilarity(dataModel);
            case CITYBLOCK:
                //曼哈顿距离：CityBlockSimilarity
                return new CityBlockSimilarity(dataModel);
            case EUCLIDEAN:
            default:
                //欧氏距离：EuclideanDistanceSimilarity
                return new EuclideanDistanceSimilarity(dataModel);
        }
    }

   /* public static ClusterSimilarity clusterSimilarity(SIMILARITY type, UserSimilarity us) throws TasteException {
        switch (type) {
            case NEAREST_NEIGHBOR_CLUSTER:
                return new NearestNeighborClusterSimilarity(us);
            case FARTHEST_NEIGHBOR_CLUSTER:
            default:
                return new FarthestNeighborClusterSimilarity(us);
        }
    }*/

    /**
     * neighborhood
     */
    public enum NEIGHBORHOOD {
        NEAREST, THRESHOLD
    }

    /**
     * 用户距离
     * @param type
     * @param s
     * @param m
     * @param num
     * @return
     * @throws TasteException
     */
    public static UserNeighborhood userNeighborhood(NEIGHBORHOOD type, UserSimilarity s, DataModel m, double num) throws TasteException {
        switch (type) {
            case NEAREST:
            /*NearestNUserNeighborhood
            指定距离最近的N个用户作为邻居。
            示例：UserNeighborhood unb = new NearestNUserNeighborhood(10, us, dm);
            三个参数分别是：邻居的个数，用户相似度，数据模型
            */
                return new NearestNUserNeighborhood((int) num, s, m);
            case THRESHOLD:
            default:
            /*ThresholdUserNeighborhood
            指定距离最近的一定百分比的用户作为邻居。
            示例：UserNeighborhood unb = new ThresholdUserNeighborhood(0.2, us, dm);
            三个参数分别是：阀值（取值范围0到1之间），用户相似度，数据模型*/
                return new ThresholdUserNeighborhood(num, s, m);
        }
    }
    /**
     * recommendation
     */
    public enum RECOMMENDER {
        USER, ITEM
    }

    /**
     * GenericUserBasedRecommender
     * 算法：
     * 1.基于用户的相似度
     * 2.相近的用户定义与数量
     * 特点：
     * 1.易于理解
     * 2.用户数较少时计算速度快
     * @param us
     * @param un
     * @param pref
     * @return
     * @throws TasteException
     */
    public static RecommenderBuilder userRecommender(final UserSimilarity us, final UserNeighborhood un, boolean pref) throws TasteException {
        return pref ? new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                return new GenericUserBasedRecommender(model, un, us);
            }
        } : new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                return new GenericBooleanPrefUserBasedRecommender(model, un, us);
            }
        };
    }

    /**
     * GenericItemBasedRecommender
     * 算法：
     * 1.基于item的相似度
     * 特点：
     * 1.item较少时就算速度更快
     * 2.当item的外部概念易于理解和获得是非常有用
     * @param is
     * @param pref
     * @return
     * @throws TasteException
     */
    public static RecommenderBuilder itemRecommender(final ItemSimilarity is, boolean pref) throws TasteException {

        return pref ? new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                return new GenericItemBasedRecommender(model, is);
            }
        } : new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                return new GenericBooleanPrefItemBasedRecommender(model, is);
            }
        };
    }


    /**
     * SlopeOneRecommender（itemBased）
     * 算法：
     * 1.基于SlopeOne算法（打分差异规则）0.8以上版本不存在,或者有相关替代，我没找到。。
     * 特点
     * 速度快
     * 需要预先计算
     * 当item数目十分少了也很有效
     * 需要限制diffs的存储数目否则内存增长太快
     * @return
     * @throws TasteException
     */
  /*  public static RecommenderBuilder slopeOneRecommender() throws TasteException {
        return new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new SlopeOneRecommender(dataModel);
            }

        };
    }
*/
    /**
     * KnnItemBasedRecommender （item-based）
     * 类似于GenericUserBasedRecommender 中基于相似用户的实现（基于相似的item）
     * 与GenericItemBasedRecommender 的主要区别是权重方式计算的不同（but, the weights are not the results of some similarity metric. Instead, the algorithm calculates the optimal set of weights to use between all pairs of items=>看的费劲）
     * @param is
     * @param op
     * @param n
     * @return
     * @throws TasteException
     */
    /*public static RecommenderBuilder itemKNNRecommender(final ItemSimilarity is, final Optimizer op, final int n) throws TasteException {
        return new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new KnnItemBasedRecommender(dataModel, is, op, n);
            }
        };
    }*/

    /**
     * SVDRecommender （item-based）
     * 算法
     * 基于支持向量机（item的特征以向量表示，每个维度的评价值）
     * 特点
     * 需要预计算
     * 推荐效果佳
     * @param factorizer
     * @return
     * @throws TasteException
     */
    public static RecommenderBuilder svdRecommender(final Factorizer factorizer) throws TasteException {
        return new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new SVDRecommender(dataModel, factorizer);
            }
        };
    }

    /**
     * TreeClusteringRecommender
     * 算法
     * 基于树形聚类的推荐算法
     * 特点
     * 用户数目少的时候非常合适
     * 计算速度快
     * 需要预先计算
     * @param cs
     * @param n
     * @return
     * @throws TasteException
     */
  /*  public static RecommenderBuilder treeClusterRecommender(final ClusterSimilarity cs, final int n) throws TasteException {
        return new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new TreeClusteringRecommender(dataModel, cs, n);
            }
        };
    }*/

    public static void showItems(long uid, List<RecommendedItem> recommendations, boolean skip) {
        if (!skip || recommendations.size() > 0) {
            logger.info("uid:%s,", uid);
            for (RecommendedItem recommendation : recommendations) {
                logger.info("物品:{},评分:{}", recommendation.getItemID(), recommendation.getValue());
            }
        }
        logger.info("用户{},没有推荐结果",uid);
    }

    /**
     * evaluator
     */
    public enum EVALUATOR {
        AVERAGE_ABSOLUTE_DIFFERENCE, RMS
    }

    /**
     * RecommenderEvaluator：评分器。
     * RecommenderIRStatsEvaluator：搜集推荐性能相关的指标，包括准确率、召回率等等。
     * RecommenderEvaluator有以下几种实现：
     * AverageAbsoluteDifferenceRecommenderEvaluator：计算平均差值
     * RMSRecommenderEvaluator：计算均方根差
     * RecommenderIRStatsEvaluator的实现类是GenericRecommenderIRStatsEvaluator。
     * @param type
     * @return
     */
    public static RecommenderEvaluator buildEvaluator(EVALUATOR type) {
        switch (type) {
            case RMS:
                return new RMSRecommenderEvaluator();
            case AVERAGE_ABSOLUTE_DIFFERENCE:
            default:
                return new AverageAbsoluteDifferenceRecommenderEvaluator();
        }
    }

    public static void evaluate(EVALUATOR type, RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("%s Evaluater Score:%s\n", type.toString(), buildEvaluator(type).evaluate(rb, mb, dm, trainPt, 1.0));
    }

    public static void evaluate(RecommenderEvaluator re, RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("Evaluater Score:%s\n", re.evaluate(rb, mb, dm, trainPt, 1.0));
    }

    /**
     * statsEvaluator
     */
    public static void statsEvaluator(RecommenderBuilder rb, DataModelBuilder mb, DataModel m, int topn) throws TasteException {
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = evaluator.evaluate(rb, mb, m, null, topn, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        // System.out.printf("Recommender IR Evaluator: %s\n", stats);
        logger.info("Recommender IR Evaluator: [Precision:{},Recall:{}]\n", stats.getPrecision(), stats.getRecall());
    }



}
