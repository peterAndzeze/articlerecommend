package com.article.recommend.dbsourcemanager;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 咨询平台数据库链接信息
 */
@Configuration("informationDBSourceConfig")
@MapperScan(basePackages = {"com.article.recommend.mapper.informationmapper"},sqlSessionTemplateRef ="informationDataSessionTemplate" )
public class InformationDBSourceConfig {


    @Bean("informationDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.information")
    public DataSource informationDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean("informationDataSqlSessionFactory")
    public SqlSessionFactory informationDataSqlSessionFactory(@Qualifier("informationDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:mybatis/informationmapper/*.xml"));
        return bean.getObject();
    }
    @Bean("informationDataTransactionManager")
    public PlatformTransactionManager informationDataTransactionManager(@Qualifier("informationDataSource") DataSource dataSource){
        return  new DataSourceTransactionManager(dataSource);
    }
    @Bean("informationDataSessionTemplate")
    public SqlSessionTemplate informationDataSessionTemplate(@Qualifier("informationDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
