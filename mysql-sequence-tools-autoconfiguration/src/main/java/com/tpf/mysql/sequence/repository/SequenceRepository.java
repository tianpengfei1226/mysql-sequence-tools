package com.tpf.mysql.sequence.repository;

/**
 * <p> 序列仓储接口 </p >
 *
 * @author : tianpf
 * @version :  SequenceRepository.java,v 1.0, 2020/8/10-18:45 Exp $
 */
public interface SequenceRepository {

    /**
     * 刷新
     *
     * @param sequenceName
     */
    void flush(String sequenceName);

    /**
     * 获取下一个序列号
     *
     * @param sequenceName
     * @return
     */
    Long next(String sequenceName);

}
