package com.article.recommend.hadoop.contentbasedrecommend;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于文章内容的推荐
 */
public class ContentBasedRecommender {

    public static void main(String[] args) {
        Map<Integer,List<Integer>> map=new HashMap<>();
        map.put(1, Arrays.asList(2,3,4));
        System.out.println(JSON.toJSONString(map));
    }

}
