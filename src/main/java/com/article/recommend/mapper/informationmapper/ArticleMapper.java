package com.article.recommend.mapper.informationmapper;

import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.vo.DataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by sw on 2018/3/18.
 */
@Mapper
public interface ArticleMapper {

    /**
     * 查询文章数据
     * @param params
     * @return
     */
    public List<ArticleInfo> queryArticles(Map<String,Object> params);

    /**
     * 当训当前日期有多少数据
     * @param params
     * @return
     */
    public DataVo getCount(Map<String,Object> params);

}