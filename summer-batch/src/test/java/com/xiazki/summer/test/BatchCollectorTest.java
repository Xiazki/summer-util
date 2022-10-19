package com.xiazki.summer.test;

import com.xiazki.summer.batch.BatchCollector;
import com.xiazki.summer.batch.BatchCollectorFactory;
import com.xiazki.summer.batch.processor.BatchCallbackProcessor;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BatchCollectorTest {

    @Test
    public void test() {
        BatchCollector<String, Integer> collector = BatchCollectorFactory.create(5, 2, 10L, TimeUnit.MILLISECONDS, new BatchCallbackProcessor<String, Integer>() {
            public Integer process(List<String> dataList) {
                return dataList.size();
            }
        });

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(()->{
                while (true){
                    Integer test = collector.collect("test");
                    System.out.println(Thread.currentThread().getName() + ":" + test);
                }
            });
            t.start();
        }
    }

    public static void main(String[] args) {
        BatchCollector<String, Integer> collector = BatchCollectorFactory.create(5, 2, 10L, TimeUnit.MILLISECONDS, new BatchCallbackProcessor<String, Integer>() {
            public Integer process(List<String> dataList) {
                return dataList.size();
            }
        });

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(()->{
                while (true){
                    Integer test = collector.collect("test");
                    System.out.println(Thread.currentThread().getName() + ":" + test);
                }
            });
            t.start();
        }
    }

    @Test
    public void testCollect() throws InterruptedException, ExecutionException, TimeoutException {
        BatchCollector<String, Integer> collector = BatchCollectorFactory.create(5, 2, 10L, TimeUnit.MILLISECONDS, new BatchCallbackProcessor<String, Integer>() {
            public Integer process(List<String> dataList) {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return dataList.size();
            }
        });

//        Integer collect = collector.collect("1");
        Future<Integer> integerFuture = collector.collectAsync("1");
        integerFuture.get(5,TimeUnit.SECONDS);

    }



}
