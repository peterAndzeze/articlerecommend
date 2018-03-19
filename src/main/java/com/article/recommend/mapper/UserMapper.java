package com.article.recommend.mapper;

import com.article.recommend.entity.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sw on 2018/3/18.
 */
@Mapper
public interface UserMapper {
    /**
     * 查询所有
     * @return
     */
    public List<UserInfo> getAll();

    /**
     * 根据user ID获取用户
      * @param id 用户编号
     * @return 用户实体
     */
    public UserInfo getOne(Long id);

    /**
     * 插入用户数据
     * @param userInfo
     */
    public void insert(UserInfo userInfo);

    /**
     * 根据用户编号修改其它属性字段
     * @param userInfo
     */
    public void update(UserInfo userInfo);

    /**
     * 根据id删除用户
     * @param id
     */
    public void delete(Long id);

    /**
     * 获取所有用户的编号
     * @return
     */
    public List<Long> getUserIds();

    /**
     * 获取用户偏好词集合
     * @param userIds 用户编号集合
     * @return
     */
    public List<UserInfo> getUserPrefList(@Param("userIds") List<Long> userIds);

    /**
     * 更新用户偏好词
     * @param userInfos
     */
    public void updateUserPrefListById(List<UserInfo> userInfos );
}