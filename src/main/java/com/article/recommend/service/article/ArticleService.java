package com.article.recommend.service.article;

import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.mapper.informationmapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    /**
     * 根据文章id获取文章内容嘻嘻你
     * @param id
     * @return
     */
    public ArticleInfo getArticleById(Long id){
        return articleMapper.getOne(id);
    }

    /**
     * 插入
     *
     * @param articleInfoList
     */
    public void insertArticles(List<ArticleInfo> articleInfoList){
        articleMapper.inserArticles(articleInfoList);
    }
}
