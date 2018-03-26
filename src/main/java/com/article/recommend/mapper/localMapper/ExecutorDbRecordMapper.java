package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.ExecuteDbRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 执行数据操作记录
 */
@Mapper
public interface ExecutorDbRecordMapper {
    public ExecuteDbRecord getExecuteDbRecord(@Param("executeType") String executeType);

    /**
     * 更新记录
     * @param executeDbRecord
     */
    public void updateExecuteDbRecord(ExecuteDbRecord executeDbRecord);
}
