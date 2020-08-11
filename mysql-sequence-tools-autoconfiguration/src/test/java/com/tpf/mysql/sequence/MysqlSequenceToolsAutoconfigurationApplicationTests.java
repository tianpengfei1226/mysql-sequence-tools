package com.tpf.mysql.sequence;

import com.tpf.mysql.sequence.repository.SequenceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MysqlSequenceToolsAutoconfigurationApplicationTests {

    @Autowired
    SequenceRepository sequenceRepository;


    @Test
    void contextLoads() {

        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));

        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
        System.out.println(sequenceRepository.next("tpf-test"));
    }

}
