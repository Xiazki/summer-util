package com.xiazki.summer.batch;

import java.util.concurrent.TimeUnit;

public class BatchConfig {
    /**
     * 攒批数据
     */
    private Integer size;

    private Integer startTime;

    private Integer maxWaitTime;
    /**
     * 攒批时间
     */
    private Long waitTime;

    private TimeUnit waitTimeUtil;

    /**
     * 处理线程数
     */
    private Integer threadCount;

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public TimeUnit getWaitTimeUtil() {
        return waitTimeUtil;
    }

    public void setWaitTimeUtil(TimeUnit waitTimeUtil) {
        this.waitTimeUtil = waitTimeUtil;
    }
}