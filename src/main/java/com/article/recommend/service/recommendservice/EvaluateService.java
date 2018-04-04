package com.article.recommend.service.recommendservice;

import com.article.recommend.entity.EvaluateValueInfo;
import com.article.recommend.mapper.localMapper.EvaluateValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(value = "localDataTransactionManager")
public class EvaluateService {
    @Autowired
    private EvaluateValueMapper evaluateValueMapper;

    /**
     * 全量
     * @return
     */
    public List<EvaluateValueInfo> getAllEvaluatorInfos(){
       return evaluateValueMapper.getAllEvaluatorInfos();
    }

    /**
     * 根据时间获取
     * @param createTime
     * @return
     */
    public List<EvaluateValueInfo> getEvaluatorInfos(String createTime){
        return evaluateValueMapper.getEvaluatorInfos(createTime);
    }

    /**
     * 批量插入
     * @param evaluateValueInfos
     */
    public void insertEvaluatorInfo(List<EvaluateValueInfo> evaluateValueInfos){
        evaluateValueMapper.insertEvaluatorInfo(evaluateValueInfos);
    }

}
