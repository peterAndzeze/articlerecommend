package com.article.recommend;

import com.article.recommend.dbsourcemanager.PropertiesModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProperModelTest {
    private Logger logger= LoggerFactory.getLogger(ProperModelTest.class);
    @Autowired
    private PropertiesModel propertiesModel;
    @Test
    public void getProperties(){
        logger.info("信息:{}{}",propertiesModel.getDriverClassName(),"失败");
        System.out.println(propertiesModel.getInfromationHost());
    }
}
