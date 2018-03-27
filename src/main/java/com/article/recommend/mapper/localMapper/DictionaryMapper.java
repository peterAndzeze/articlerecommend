package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.DictionaryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryMapper {
    /**
     * 根据key获取字典值
     * @param key
     * @return
     */
    public DictionaryInfo getDictionaryByKey(String key);

}

