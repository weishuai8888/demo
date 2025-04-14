package com.weishuai.common.exception.streamException;


/**
 * @Description : consumer
 * @Author : Future Buddha
 * @Date: 2022-03-08 10:19
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}
