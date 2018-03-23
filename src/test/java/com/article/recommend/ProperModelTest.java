package com.article.recommend;

import com.article.recommend.dbsourcemanager.PropertiesModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProperModelTest {
    @Autowired
    private PropertiesModel propertiesModel;
    @Test
    public void getProperties(){
        System.out.println(propertiesModel.getInfromationHost());
    }
}
