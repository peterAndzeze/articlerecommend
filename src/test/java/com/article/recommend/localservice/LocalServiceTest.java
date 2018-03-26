package com.article.recommend.localservice;

import com.article.recommend.entity.ExecuteDbRecord;
import com.article.recommend.service.executedbservice.ExecuteDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalServiceTest {
    @Autowired
    ExecuteDbService executeDbService;
    @Test
    public void getExecuteDbRecorde(){
        ExecuteDbRecord executeDbRecord=executeDbService.getExecuteDbRecord("0");
        System.out.println(executeDbRecord.getLimitId()+"**************");
    }
}

