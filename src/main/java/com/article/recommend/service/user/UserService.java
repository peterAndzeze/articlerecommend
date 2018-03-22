package com.article.recommend.service.user;

import com.article.recommend.entity.UserInfo;
import com.article.recommend.mapper.bigDataMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 获取用户编号集合
     * @return
     */
    public List<Long> getUserIds(){

        return userMapper.getUserIds();


    }

    /**
     * 获取用户偏好词集合
     * @param userIds 用户编号
     * @return
     */
    public  List<UserInfo> getUserPrefList(List<Long> userIds){
        return userMapper.getUserPrefList(userIds);
    }

    /**
     * 执行批量更新用户偏好词信息
     * @param userInfos
     */
    public void updateBatchUserPrefListById(List<UserInfo> userInfos){
        String sql= "update capec.tb_users set pref_list=? where id=?";
        jdbcTemplate.batchUpdate(sql,setParameters(userInfos));
    }
    /**
     * 设置参预置数
     * @param listUser
     * @return
     */
    private List<Object[]> setParameters(List<UserInfo> listUser){
        List<Object[]> parameters = new ArrayList<Object[]>();
        for (UserInfo u : listUser) {
            parameters.add(new Object[] { u.getPrefList(),u.getId()});
        }
        return parameters;
    }

}
