package com.article.recommend.userservice;

import com.article.recommend.entity.UserInfo;
import com.article.recommend.mapper.bigDataMapper.UserMapper;
import com.article.recommend.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sw on 2018/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @Test
    public void getUser(){
        System.out.println("get userMapper:"+userMapper);
        UserInfo userInfo=userMapper.getOne(1L);
        System.out.println(userInfo.getName()+"*****");
    }
    @Test
    public void getUserIds(){
        List<Long> userIds=userService.getUserIds();
        System.out.println("userids:"+userIds.size()+"************");
    }
    @Test
    public void getUserPrefList(){
        List<Long> userIds= Arrays.asList(1L,2L);
        List<UserInfo> userInfos=userService.getUserPrefList(userIds);
        userInfos.forEach(e -> System.out.println(e.getPrefList()+"****************"));
    }
    @Test
    @Transactional(rollbackForClassName = {"Exception"})
    @Rollback(value = false)
    public void batchUpdateUserPrefListById(){
        String sql= "update capec.tb_users set pref_list=? where id=?";
        List<UserInfo> listUser=new ArrayList<>();
        UserInfo userInfo =new UserInfo();
        userInfo.setId(1L);
        userInfo.setPrefList("{1:{\"学神\":0.129}}");
        UserInfo userInfo1 =new UserInfo();
        userInfo1.setId(2L);
        userInfo1.setPrefList(null);
        listUser.add(userInfo);
        listUser.add(userInfo1);
        //jdbcTemplate.batchUpdate(sql,setParameters(listUser));

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