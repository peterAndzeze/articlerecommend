package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.entity.QuartzInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuartzMapper {
    /**
     * 获取所有
     * @return
     */
    public List<QuartzInfo> getQuartzInfos();

    /**
     * 获取单笔
     * @param id
     * @return
     */
    public QuartzInfo getQuartzInfoById(Long id);

    /**
     * 更新
     * @param quartzInfo
     */
    public void updateQuartz(QuartzInfo quartzInfo);

    /**
     * 新增
     * @param quartzInfo
     */
    public void inserQuartz(QuartzInfo quartzInfo);

    /**
     * 删除
     * @param id
     */
    public void deleteQuartz(Long id);

}

