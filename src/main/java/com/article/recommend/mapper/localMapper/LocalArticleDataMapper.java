package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 执行数据导入mapper
 */
@Mapper
public interface LocalArticleDataMapper {
    /**
     * 导入文章数据
     * @param articleInfos
     */
    public void insertLocalArticle(@Param("articleInfos") List<ArticleInfo> articleInfos);

    /**
     * 删除过期的文章数据
     * @param businessDate
     */
    public void deleteLoseArticleData(String businessDate);

    /**
     * 插入要删除的数据
     * @param businessDate 小于当前日期的
     */
    public void insertLoseArticleData(String businessDate);





}
