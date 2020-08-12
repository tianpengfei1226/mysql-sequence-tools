package com.tpf.mysql.sequence.repository;

import com.tpf.mysql.sequence.dao.SequenceDAO;
import com.tpf.mysql.sequence.domain.Sequence;
import com.tpf.mysql.sequence.exception.SequenceUpdateException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> 序列仓储实现类 </p >
 *
 * @author : tianpf
 * @version :  SequenceRepositoryImpl.java,v 1.0, 2020/8/10-18:46 Exp $
 */

public class SequenceRepositoryImpl implements SequenceRepository, InitializingBean {

    @Resource
    private SequenceDAO sequenceDAO;
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> sequenceQueue = new ConcurrentHashMap();
    private ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap();
    private ConcurrentHashMap<String, AtomicInteger> counts = new ConcurrentHashMap();
    @Resource(name = "sequenceThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor sequenceThreadPoolTaskExecutor;
    @Resource
    private TransactionTemplate transactionTemplate;

    private Map<String, Integer> thresholds = new HashMap();
    private Map<String, Integer> totals = new HashMap();

    @Override
    public void flush(String sequenceName) {
        if (this.overThreshold(sequenceName)) {
            try {
                ((ReentrantLock) this.locks.get(sequenceName)).lock();
                if (this.overThreshold(sequenceName)) {
                    try {
                        this.flushBuffer(sequenceName);
                    } catch (SequenceUpdateException var6) {
                        this.asyncFlush(sequenceName);
                    }
                }
            } finally {
                ((ReentrantLock) this.locks.get(sequenceName)).unlock();
            }
        }

    }

    private void flushBuffer(final String sequenceName) {
        this.transactionTemplate.execute((transactionStatus) -> {
            Sequence sequence = SequenceRepositoryImpl.this.sequenceDAO.lock(sequenceName);
            if (sequence == null) {
                throw new RuntimeException("序列" + sequenceName + "没有初始化.");
            } else {
                if (SequenceRepositoryImpl.this.thresholds.get(sequence.getName()) == null) {
                    SequenceRepositoryImpl.this.thresholds.put(sequence.getName(), sequence.getThreshold());
                }

                if (SequenceRepositoryImpl.this.totals.get(sequence.getName()) == null) {
                    totals.put(sequence.getName(), sequence.getTotal());
                }

                if (SequenceRepositoryImpl.this.locks.get(sequence.getName()) == null) {
                    SequenceRepositoryImpl.this.locks.put(sequence.getName(), new ReentrantLock());
                }

                int increment = sequence.getIncrement();
                int total = sequence.getTotal();
                Integer offset = increment * total;
                Long maxValue = sequence.getMaxValue() != null && sequence.getMaxValue() > 0L ? sequence.getMaxValue() : 9223372036854775807L;
                Long beforeValue = sequence.getCurrentValue();

                Long afterValue;
                for (afterValue = beforeValue + (long) offset; afterValue > maxValue; afterValue = afterValue - maxValue) {
                }

                try {
                    SequenceRepositoryImpl.this.sequenceDAO.update(sequenceName, beforeValue, afterValue);
                } catch (Exception e) {
                    throw new SequenceUpdateException(e);
                }

                // 没有就创建，否则延用之前的序列。每次都新建会导致序列断层
                ConcurrentLinkedQueue newQueue = Optional.ofNullable(sequenceQueue.get(sequenceName)).orElse(new ConcurrentLinkedQueue());

                for (int i = 0; i < total; ++i) {
                    newQueue.add(beforeValue);
                    beforeValue = beforeValue + (long) increment;
                    if (beforeValue > maxValue) {
                        beforeValue = beforeValue - maxValue;
                    }
                }

                SequenceRepositoryImpl.this.sequenceQueue.put(sequenceName, newQueue);
                SequenceRepositoryImpl.this.counts.put(sequenceName, new AtomicInteger(0));
            }

            return null;
        });
    }

    @Override
    public Long next(String sequenceName) {
        ConcurrentLinkedQueue<Long> queue = (ConcurrentLinkedQueue) this.sequenceQueue.get(sequenceName);
        if (queue == null) {
            this.flushBuffer(sequenceName);
            queue = (ConcurrentLinkedQueue) this.sequenceQueue.get(sequenceName);
        }

        Long sequence = (Long) queue.poll();
        if (sequence != null) {
            ((AtomicInteger) this.counts.get(sequenceName)).incrementAndGet();
            if (this.overThreshold(sequenceName)) {
                this.asyncFlush(sequenceName);
            }

            return sequence;
        } else {
            try {
                this.flush(sequenceName);
                return this.next(sequenceName);
            } catch (Exception e) {
                return this.next(sequenceName);
            }
        }
    }

    private boolean overThreshold(String sequenceName) {
        return ((AtomicInteger) this.counts.get(sequenceName)).intValue() >= (Integer) this.totals.get(sequenceName) - (Integer) this.thresholds.get(sequenceName);
    }

    private void asyncFlush(final String sequenceName) {
        try {
            this.sequenceThreadPoolTaskExecutor.execute(() -> SequenceRepositoryImpl.this.flush(sequenceName));
        } catch (TaskRejectedException e) {
            // TODO

        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.sequenceQueue == null || this.sequenceQueue.isEmpty()) {
            List<Sequence> sequenceList = this.sequenceDAO.loadAll();
            Iterator iterator = sequenceList.iterator();

            while (iterator.hasNext()) {
                Sequence sequence = (Sequence) iterator.next();
                this.thresholds.put(sequence.getName(), sequence.getThreshold());
                this.totals.put(sequence.getName(), sequence.getTotal());
                this.locks.put(sequence.getName(), new ReentrantLock());
                this.flushBuffer(sequence.getName());
            }
        }

        System.out.println("-----------------");
    }
}
