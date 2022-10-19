package com.xiazki.summer.batch.entity;

import com.google.common.util.concurrent.SettableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class BatchEntity<T, R> {

    private Integer maxCount;
    private Long startTime;
    private Long maxWaitTime;
    private List<T> dataList = new ArrayList<>();
    private SettableFuture<R> future;

    public BatchEntity(Integer maxCount, Long maxWaitTime) {
        this.maxCount = maxCount;
        this.maxWaitTime = maxWaitTime;
        this.startTime = System.currentTimeMillis();
    }

    public Future<R> append(T data) {
        if (dataList.size() == 0 || future == null) {
            future = SettableFuture.create();
        }
        this.dataList.add(data);
        return future;
    }

    public void clear() {
        dataList.clear();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void onSuccess(R result) {
        future.set(result);
    }

    public void onException(Throwable throwable) {
        future.setException(throwable);
    }

    public boolean isFull() {
        return dataList.size() >= maxCount || (maxWaitTime != null && (System.currentTimeMillis() - startTime) >= maxWaitTime);
    }
}