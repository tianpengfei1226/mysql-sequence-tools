package com.tpf.mysql.sequence.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  DatasourceConfig.java,v 1.0, 2020/8/11-10:27 Exp $
 */
@Configuration
public class DatasourceConfig {

//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource dataSource(){
//        return new DruidDataSource();
//    }

    @Autowired
    DataSource dataSource;

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(){
        return new TransactionTemplate(transactionManager());
    }

}
