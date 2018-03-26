package com.article.recommend.service.executedbservice;

import com.article.recommend.entity.ExecuteDbRecord;
import com.article.recommend.mapper.localMapper.ExecutorDbRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 执行数据导入记录信息
 */
@Service
public class ExecuteDbService {
    @Autowired
    private ExecutorDbRecordMapper executorDbRecordMapper;

    /**
     * 获取执行类型
     * @param executeType
     * @return
     */
    public ExecuteDbRecord getExecuteDbRecord(String executeType){
        return executorDbRecordMapper.getExecuteDbRecord(executeType);
    }

    /**
     * 更新记录
     * @param executeDbRecord
     */
    public void updateExecuteDbRecord(ExecuteDbRecord executeDbRecord){
        executorDbRecordMapper.updateExecuteDbRecord(executeDbRecord);
    }
}
