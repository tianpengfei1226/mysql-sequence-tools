package com.tpf.mysql.sequence.exception;

/**
 * <p> 序列更新异常 </p >
 *
 * @author : tianpf
 * @version :  SequenceUpdateException.java,v 1.0, 2020/8/10-18:39 Exp $
 */
public class SequenceUpdateException extends RuntimeException {

    private static final long serialVersionUID = 4027445427110584539L;

    public SequenceUpdateException(Exception e) {
        super(e);
    }
}
