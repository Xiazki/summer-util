package com.xiazki.summer.batch;

import com.xiazki.summer.batch.entity.BatchEntity;
import com.xiazki.summer.batch.entity.RunTask;
import com.xiazki.summer.batch.executor.TaskExecutor;
import com.xiazki.summer.batch.processor.BatchCallbackProcessor;

import java.util.concurrent.*;


public class DefaultBatchCollector<T, R> implements BatchCollector<T, R> {

    private volatile BatchEntity<T, R> currentEntity;
    private BatchConfig config;
    private BatchCallbackProcessor<T, R> processor;
    private TaskExecutor taskExecutor;
    private int maxCount = 1;
    private Long maxWaitTime = 1000L;
    private TimeUnit waitTimeUtil = TimeUnit.MILLISECONDS;
    private ScheduledExecutorService scheduledExecutor;
    private boolean timed;

    public DefaultBatchCollector(BatchConfig config, BatchCallbackProcessor<T, R> processor) {
        this.config = config;
        this.processor = processor;
        this.taskExecutor = new TaskExecutor(config.getThreadCount());
        if (config.getSize() != null) {
            this.maxCount = config.getSize();
        }
        this.maxWaitTime = config.getWaitTime();
        this.waitTimeUtil = config.getWaitTimeUtil();
        timed = maxWaitTime != null && waitTimeUtil != null;
        initCheckTimeOut();
    }

    @Override
    public Future<R> collectAsync(T data) {
        synchronized (this) {
            if (currentEntity == null) {
                Long waitTime = timed ? waitTimeUtil.toMillis(maxWaitTime) : null;
                currentEntity = new BatchEntity<>(maxCount, waitTime);
            }
            Future<R> future = currentEntity.append(data);
            flushIfFull();
            return future;
        }
    }

    @Override
    public R collect(T data) {
        return get(collectAsync(data));
    }

    private R get(Future<R> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new BatchException(e);
        }
    }

    private void flushIfFull() {
        if (currentEntity != null) {
            synchronized (this) {
                if (currentEntity.isFull()) {
                    taskExecutor.submit(new RunTask<>(currentEntity, processor));
                    currentEntity = null;
                }
            }
        }
    }

    public void initCheckTimeOut() {
        if (waitTimeUtil != null && maxWaitTime != null) {
            this.scheduledExecutor = new ScheduledThreadPoolExecutor(1);
            scheduledExecutor.schedule(this::flushIfFull, maxWaitTime, waitTimeUtil);
        }
    }
}