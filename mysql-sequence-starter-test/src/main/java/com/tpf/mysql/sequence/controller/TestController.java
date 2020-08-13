package com.tpf.mysql.sequence.controller;

import com.tpf.mysql.sequence.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  TestController.java,v 1.0, 2020/8/11-13:51 Exp $
 */
@RestController
public class TestController {

    @Autowired
    SequenceRepository sequenceRepository;

    @GetMapping("/test-seq")
    public String testSequence() {
        Long next = sequenceRepository.next("tpf-test");
        return next + "";
    }
}
