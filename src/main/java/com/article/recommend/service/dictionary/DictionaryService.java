package com.article.recommend.service.dictionary;

import com.article.recommend.entity.DictionaryInfo;
import com.article.recommend.mapper.localMapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper;

    public List<DictionaryInfo> queryDictionarys(){
        return dictionaryMapper.queryDictionarys();
    }

    public DictionaryInfo getDictionayById(Long id){
        return dictionaryMapper.getDictionayById(id);
    }

    public  void updateDictionary(DictionaryInfo dictionaryInfo){
        dictionaryMapper.updateDictionary(dictionaryInfo);
    }

    /**
     * 根据key获取
     * @param key
     * @return
     */
    public DictionaryInfo getDictionaryByKey(String key){
        return dictionaryMapper.getDictionaryByKey(key);
    }
}
