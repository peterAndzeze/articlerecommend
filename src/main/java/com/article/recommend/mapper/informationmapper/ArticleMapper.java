package com.article.recommend.mapper.informationmapper;

import com.article.recommend.entity.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sw on 2018/3/18.
 */
@Mapper
public interface ArticleMapper {
    /**
     * 查询所有
     * @return
     */
    public List<ArticleInfo> getAll();

    /**
     * 根据文章 ID获取用户
      * @param id 文章编号
     * @return 文章实体
     */
    public ArticleInfo getOne(Long id);

    /**
     * 插入文章数据
     * @param articleInfo
     */
    public void insert(ArticleInfo articleInfo);

    /**
     * 根据文章编号修改其它属性字段
     * @param articleInfo
     */
    public void update(ArticleInfo articleInfo);

    /**
     * 根据id删除文章
     * @param id
     */
    public void delete(Long id);

    /**
     * batch insert
     * @param articleInfos
     */
    public void inserArticles(@Param("articleInfos") List<ArticleInfo> articleInfos);

}