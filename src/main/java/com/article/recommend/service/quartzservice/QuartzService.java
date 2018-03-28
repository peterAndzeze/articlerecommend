package com.article.recommend.service.quartzservice;

import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.mapper.localMapper.QuartzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务
 */
@Service
public class QuartzService {
    @Autowired
    private QuartzMapper quartzMapper;
    public List<QuartzInfo> getQuartzInfos(){
        return quartzMapper.getQuartzInfos();
    }

    public QuartzInfo getQuartzInfoById(Long id){
        return quartzMapper.getQuartzInfoById(id);
    }

    /**
     * 更新
     * @param quartzInfo
     */
    public void updateQuartzInfo(QuartzInfo quartzInfo){
        quartzMapper.updateQuartz(quartzInfo);
    }
}
