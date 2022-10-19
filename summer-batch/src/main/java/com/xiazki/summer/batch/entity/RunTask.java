package com.xiazki.summer.batch.entity;

import com.xiazki.summer.batch.processor.BatchCallbackProcessor;

public class RunTask<T, R> implements Runnable {

    private BatchEntity<T, R> entity;

    private BatchCallbackProcessor<T, R> processor;

    public RunTask(BatchEntity<T, R> entity, BatchCallbackProcessor<T, R> processor) {
        this.entity = entity;
        this.processor = processor;
    }


    @Override
    public void run() {
        try {
            R result = this.processor.process(entity.getDataList());
            entity.onSuccess(result);
        } catch (Exception e) {
            entity.onException(e);
        } finally {
            entity.clear();
        }

    }


}