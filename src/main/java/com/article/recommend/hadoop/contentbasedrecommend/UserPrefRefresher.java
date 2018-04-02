package com.article.recommend.hadoop.contentbasedrecommend;

import com.article.recommend.Util.CustomerHashMap;
import com.article.recommend.Util.JsonKit;
import com.article.recommend.entity.UserInfo;
import com.article.recommend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 刷新用户对应的关键词
 */
public class UserPrefRefresher {
    //设置TFIDF提取的关键词数目
    private static final int KEY_WORDS_NUM = 10;
    @Autowired
    private UserService userService;

    //每日衰减系数
    private static final double DEC_COEE=0.7;
    public void refresh(){
        refresh(userService.getUserIds());
    }

    public void refresh(List<Long> userIds){
        //首先对用户的喜好关键词列表进行衰减更新


    }

    /**
     * 对用户的喜好关键词列表进行衰减更新
     * @param userIds 用户编号集合
     */
    public void autoDecRefresh(List<Long> userIds){
        if(null== userIds || userIds.isEmpty()){//空的
            return;
        }
        //用以更新的用户喜好关键词map的json串
        //用于删除喜好值过低的关键词
        ArrayList<String> keywordToDelete=new ArrayList<String>();
        List<UserInfo> userPrefList=userService.getUserPrefList(userIds);
        userPrefList.forEach(userInfo ->
                {
                    String newPrefList = "{";
                    HashMap<Integer, CustomerHashMap<String, Double>> map = JsonKit.jsonPrefListtoMap(userInfo.getPrefList());
                    for (Map.Entry<Integer,CustomerHashMap<String,Double>> mapEntry:map.entrySet()){
                        Integer topicId=mapEntry.getKey();//得到主题编号
                        CustomerHashMap<String,Double> biaoQians=mapEntry.getValue();
                        newPrefList+="\""+topicId+"\":";
                        //标签为空
                        if(!biaoQians.toString().equals("{}")){
                            for(Map.Entry<String,Double> stringDoubleEntry:biaoQians.entrySet()){
                                String key=stringDoubleEntry.getKey();
                                //累计TFIDF值乘以衰减系数
                                Double result=stringDoubleEntry.getValue()*DEC_COEE;
                                if(result<10 && result>0){
                                   // keywordToDelete.add(key);//删除该标签的
                                }
                                biaoQians.put(key,result);
                            }
                        }
                        //先不删除
                      /*  for(String deleteKey:keywordToDelete){
                            biaoQians.remove(deleteKey);
                        }
                        keywordToDelete.clear();*/
                        newPrefList+=biaoQians.toString()+",";
                    }
                    newPrefList="'"+newPrefList.substring(0,newPrefList.length()-1)+"}'";
                    userInfo.setPrefList(newPrefList);
                }
        );
        //执行批量更新
        userService.updateBatchUserPrefListById(userPrefList);
    }

    public void userViewHistory(){

    }

}
