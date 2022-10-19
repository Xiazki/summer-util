package com.xiazki.summer.batch.processor;

import java.util.List;

/**
 * 批量处理回调处理器
 *
 * @param <T> 数据
 * @param <R> 返回值
 */
public interface BatchCallbackProcessor<T, R> {

    R process(List<T> dataList);

}