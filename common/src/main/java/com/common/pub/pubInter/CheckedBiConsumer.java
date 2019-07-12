package com.common.pub.pubInter;

@FunctionalInterface
public interface CheckedBiConsumer<T, U>{
    void accept(T t, U u) throws Exception;
}

