package com.article.recommend.dbsourcemanager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db")
@PropertySource(value = "classpath:db.properties")
public class PropertiesModel {
    private String infromationHost;
    private String informationUser;

    public String getInfromationHost() {
        return infromationHost;
    }

    public void setInfromationHost(String infromationHost) {
        this.infromationHost = infromationHost;
    }

    public String getInformationUser() {
        return informationUser;
    }

    public void setInformationUser(String informationUser) {
        this.informationUser = informationUser;
    }


}
