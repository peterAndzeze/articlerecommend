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
    private String informationPassword;
    private String driverClassName;

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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getInformationPassword() {
        return informationPassword;
    }

    public void setInformationPassword(String informationPassword) {
        this.informationPassword = informationPassword;
    }
}
