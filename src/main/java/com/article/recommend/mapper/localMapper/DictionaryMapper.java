package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.DictionaryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictionaryMapper {
    /**
     * 根据key获取字典值
     * @param key
     * @return
     */
    public DictionaryInfo getDictionaryByKey(String key);

    /**
     * 查询所有的
     * @return
     */
    public List<DictionaryInfo> queryDictionarys();

    /**
     * 根据id
     * @param id
     * @return
     */
    public DictionaryInfo getDictionayById(Long id);

    /**
     * 更新
     * @param dictionaryInfo
     */
    public void updateDictionary(DictionaryInfo dictionaryInfo);

}

