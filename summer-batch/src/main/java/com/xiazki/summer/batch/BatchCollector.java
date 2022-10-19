package com.xiazki.summer.batch;

import java.util.concurrent.Future;

public interface BatchCollector<T,R> {

    Future<R> collectAsync(T data);

    R collect(T data);

}
