package com.xiazki.summer.batch.executor;

import com.xiazki.summer.batch.entity.RunTask;

import java.util.concurrent.*;


public class TaskExecutor {

    private ExecutorService executorService;

    public TaskExecutor(int threadCount) {
        executorService = new ThreadPoolExecutor(threadCount, threadCount, 60 * 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    }

    public <T, R> void submit(RunTask<T, R> task) {
        executorService.submit(task);
    }

}