package com.article.recommend.mapper.localMapper;

import com.article.recommend.entity.ArticleInfo;
import com.article.recommend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 执行数据导入mapper
 */
@Mapper
public interface LocalUserDataMapper {

    /**
     * 导入用户数据
     * @param userInfos
     */
    public void importUsers(List<UserInfo> userInfos);




}
