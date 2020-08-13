package com.tpf.mysql.sequence.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  DatasourceConfig.java,v 1.0, 2020/8/12-17:20 Exp $
 */

@Configuration
public class DatasourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return  new DruidDataSource();
    }

//    @Bean
//    public SqlSessionFactoryBean sessionFactory() throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//
//        // 加载MyBatis配置文件
//        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//        // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
//        sqlSessionFactoryBean.setMapperLocations(
//                resourcePatternResolver
//                        .getResources("classpath*:mybatis/mapper/**/*.xml"));
//        return sqlSessionFactoryBean;
//    }
//    @Bean
//    public SqlSession sqlSession() throws Exception {
//        return new SqlSessionTemplate(sessionFactory().getObject(), ExecutorType.BATCH);
//    }

    @Bean
    public DataSourceTransactionManager sequenceTransactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public TransactionTemplate sequenceTransactionTemplate(){
        return new TransactionTemplate(sequenceTransactionManager());
    }

}
