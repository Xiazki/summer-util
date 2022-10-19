package com.xiazki.summer.batch;

import com.xiazki.summer.batch.processor.BatchCallbackProcessor;

import java.util.concurrent.TimeUnit;

public class BatchCollectorFactory {

    private static final Integer DEFAULT_THREAD_COUNT = 1;

    public static <T, R> BatchCollector<T, R> create(Integer size, BatchCallbackProcessor<T, R> processor) {
        return create(size, DEFAULT_THREAD_COUNT, null, null, processor);
    }

    public static <T, R> BatchCollector<T, R> create(Integer size, Integer threadCount, Long waitTime, TimeUnit waitTimeUtil, BatchCallbackProcessor<T, R> processor) {
        BatchConfig config = new BatchConfig();
        config.setSize(size);
        config.setThreadCount(threadCount);
        config.setWaitTime(waitTime);
        config.setWaitTimeUtil(waitTimeUtil);
        config.setThreadCount(threadCount);
        return new DefaultBatchCollector<>(config, processor);
    }
}