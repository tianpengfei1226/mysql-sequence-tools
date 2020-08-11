package com.tpf.mysql.sequence.domain;

import java.io.Serializable;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  Sequence.java,v 1.0, 2020/8/10-18:50 Exp $
 */

public class Sequence implements Serializable {
    private static final long serialVersionUID = -505843681643456064L;
    private String name;
    private Long currentValue;
    private Integer increment;
    private Integer total;
    private Integer threshold;
    private Long maxValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }
}
