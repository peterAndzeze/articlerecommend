package com.article.recommend.recommend;

import com.article.recommend.Util.DateUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.entity.EvaluateValueInfo;
import com.article.recommend.service.dictionary.DictionaryService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于用户
 */
@Service
public class UserRecommend {
    private static Logger logger= LoggerFactory.getLogger(UserRecommend.class);
    @Autowired
    private DictionaryService dictionaryService;
    /**
     * 计算推荐结果
     * @param dataFile 数据文件
     */
    public void evaluateRecommend(String dataFile) throws IOException {
        DataModel dataModel=RecommendFactory.buildDateModel(dataFile);
        try {
            //指定距离最近的N个用户 字典暂时查多次
            DictionaryInfo dictionaryInfo=dictionaryService.getDictionaryByKey(RecommendConstant.NEAREST_NUM);
            int nearNum=(dictionaryInfo.getValue() ==null || dictionaryInfo.getValue().equals(""))? 10:Integer.valueOf(dictionaryInfo.getValue());
            //对象数据占用估计很大
            Map<Long,List<RecommendedItem>> dataMap=userRecommend(dataModel,nearNum);
            //过滤重复数据，并选择value值高的
            logger.info("过滤推荐重复start");
            for (Map.Entry<Long,List<RecommendedItem>>  entry:dataMap.entrySet()){
                List<Long> ids = new ArrayList<>(entry.getValue().size());//用来临时存储iterm的id
                //并行计算去重复
                List<RecommendedItem> newResult=entry.getValue().parallelStream().filter(v->{
                   boolean flag=!ids.contains(v.getItemID());
                   ids.add(v.getItemID());
                   return flag;
                }).collect(Collectors.toList());
                dataMap.put(entry.getKey(),newResult);
            }
            logger.info("过滤推荐重复end");

        } catch (TasteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于用户的PEARSON 指定距离内N个用户推荐
     * @param dataModel
     */
    private  Map<Long,List<RecommendedItem>> userRecommend(DataModel dataModel ,int nearNum) throws TasteException, IOException {
        //查询各个开关
        DictionaryInfo dictionaryInfo=dictionaryService.getDictionaryByKey(RecommendConstant.USER_SIM_STATE);
        DictionaryInfo numD=dictionaryService.getDictionaryByKey(RecommendConstant.RECOMMENDER_NUM);
        String[] states=dictionaryInfo.getValue().split(",");
        int recommenderNum=Integer.valueOf(numD.getValue());
        LongPrimitiveIterator userIds=dataModel.getUserIDs();//得到所有用户
        logger.info("基于用户推荐start");
        List<Recommender> useRecommenders=new ArrayList<>();
        Recommender recommender0=null;
       if(states[0].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)){//开 荡入数组
           recommender0=UserRecommender.userPearson(dataModel,nearNum);
           useRecommenders.add(recommender0);
           logger.info("基于用户推荐皮尔逊 end");
       }
        Recommender recommender1=null;
       if(states[1].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)) {
           recommender1= UserRecommender.userCosin(dataModel, nearNum);
           useRecommenders.add(recommender1);
           logger.info("基于用户推荐cosin end");
       }
        Recommender recommender2=null;
       if(states[2].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)) {
           recommender2 = UserRecommender.userTanimoto(dataModel, nearNum);
           useRecommenders.add(recommender2);
           logger.info("基于用户推荐TANIMOTO end");
       }
        Recommender recommender3=null;
       if(states[3].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)) {
           recommender3= UserRecommender.userLoglikelihood(dataModel, nearNum);
           useRecommenders.add(recommender3);
           logger.info("基于用户推荐LOGLIKELIHOOD end");
       }
        Recommender recommender4=null;
       if(states[4].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)) {
           recommender4= UserRecommender.userCityblock(dataModel, nearNum);
           useRecommenders.add(recommender4);
           logger.info("基于用户推荐CITYBLOCK end");
       }
        Recommender recommender5=null;
        if(states[5].equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE)) {
            recommender5= UserRecommender.userEuclidean(dataModel, nearNum);
            useRecommenders.add(recommender5);
            logger.info("基于用户推荐EUCLIDEAN end");
       }
       Map<Long,List<RecommendedItem>> dataMap=new HashMap<>();
        logger.info("为用户{}从{}个选择start",dataModel.getNumUsers(),dataModel.getNumItems());
        for (Recommender recommender:useRecommenders) {
           while (userIds.hasNext()) {
               Long uId = userIds.next();
               List<RecommendedItem> list=result(recommender,uId,recommenderNum);//提前判断
               if(list.size()==0 || list.isEmpty()){
                   continue;
               }
               if(dataMap.containsKey(uId)){//存在
                  dataMap.get(uId).addAll(list);
               }else{//不存在
                   dataMap.put(uId,list);
               }

           }
       }
       //扔到队列计算
       logger.info("协同率基于用户end");
        return dataMap;
    }

    /**
     * 每一个用户推荐多少
     * @param recommender
     * @param userId
     * @param recommendNum
     */
    public  List<RecommendedItem>  result(Recommender recommender,Long userId,int recommendNum) throws TasteException {
        return recommender.recommend(userId,recommendNum);
    }

    static class Person{
        Long id;
        Double value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Person(Long id, Double value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", value=" + value +
                    '}';
        }
    }
    public static void main(String[] args) throws TasteException, IOException {
        Map<Long,List<Person>> map=new HashMap<>();
        Person p=new Person(1L,1.0);
        Person p1=new Person(2L,1.3);
        Person p2=new Person(1L,1.0);
        Person p4=new Person(4L,1.5);
        List<Person> ps=Arrays.asList(p4,p,p1,p2);
        map.put(1L,ps);
        System.out.println(map.toString());
        for(Map.Entry<Long,List<Person>> entry:map.entrySet()){
            List<Long> ids=new ArrayList<>();
            List<Person> nps= entry.getValue().parallelStream().filter(v ->{
                boolean flag=!ids.contains(v.getId());
                ids.add(v.getId());
                return flag;
            }).collect(Collectors.toList());
            map.put(entry.getKey(),nps);
        }
        System.out.println(map.toString());
    }


}
