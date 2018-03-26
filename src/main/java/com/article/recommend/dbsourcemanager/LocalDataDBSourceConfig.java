package com.article.recommend.dbsourcemanager;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
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
 * 数据库链接信息
 */
@Configuration("localDataDBSourceConfig")
@MapperScan(basePackages ="com.article.recommend.mapper.localMapper" ,sqlSessionTemplateRef ="localDataSessionTemplate" )
public class LocalDataDBSourceConfig {

    @Bean("localDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.local")
    public DataSource bigDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean("localDataSqlSessionFactory")
    @Primary
    public SqlSessionFactory localDataSqlSessionFactory(@Qualifier("localDataSource") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:mybatis/localmapper/*.xml"));
        return bean.getObject();
    }
    @Bean("localDataTransactionManager")
    @Primary
    public PlatformTransactionManager localDataTransactionManager(@Qualifier("localDataSource") DataSource dataSource){
        return  new DataSourceTransactionManager(dataSource);
    }
    @Bean("localDataSessionTemplate")
    @Primary
    public SqlSessionTemplate localDataSessionTemplate(@Qualifier("localDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
