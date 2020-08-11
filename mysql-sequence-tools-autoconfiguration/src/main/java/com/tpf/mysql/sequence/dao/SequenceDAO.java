package com.tpf.mysql.sequence.dao;


import com.tpf.mysql.sequence.domain.Sequence;

import java.util.List;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  SequenceDAO.java,v 1.0, 2020/8/10-18:49 Exp $
 */
public interface SequenceDAO {

    /**
     * 加载所有的序列
     *
     * @return
     */
    List<Sequence> loadAll();

    /**
     * 锁定
     *
     * @param sequenceName
     * @return
     */
    Sequence lock(String sequenceName);

    /**
     * 更新
     *
     * @param sequenceName
     * @param beforeValue
     * @param afterValue
     */
    void update(String sequenceName, Long beforeValue, Long afterValue);


}
