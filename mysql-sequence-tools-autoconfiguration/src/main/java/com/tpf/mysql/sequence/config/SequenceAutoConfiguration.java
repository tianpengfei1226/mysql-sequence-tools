package com.tpf.mysql.sequence.config;

import com.tpf.mysql.sequence.dao.SequenceDAO;
import com.tpf.mysql.sequence.dao.SequenceDAOImpl;
import com.tpf.mysql.sequence.repository.SequenceRepository;
import com.tpf.mysql.sequence.repository.SequenceRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 序列自动配置类 </p >
 *
 * @author : tianpf
 * @version :  SequenceConfig.java,v 1.0, 2020/8/11-9:54 Exp $
 */

@Configuration
public class SequenceAutoConfiguration {

    @Bean
    public SequenceRepository sequenceRepository(){
        return new SequenceRepositoryImpl();
    }

    @Bean
    public SequenceDAO sequenceDAO(){
        return new SequenceDAOImpl();
    }


}
