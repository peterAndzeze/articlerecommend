package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.EvaluateValueInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 推荐评分结果
 */
@Mapper
public interface EvaluateValueMapper {
    /**
     * 全量获取
     * @return
     */
    public List<EvaluateValueInfo> getAllEvaluatorInfos();
    /**
     * 根据时间查询
     * @param createTime
     * @return
     */
    public List<EvaluateValueInfo> getEvaluatorInfos(String createTime);

    /**
     * 批量插入
     * @param evaluatorInfos
     */
    public void insertEvaluatorInfo(List<EvaluateValueInfo> evaluatorInfos);

}
