package com.common.pub.pubInter;

@FunctionalInterface
public interface CheckedConsumer<T>{
    void accept(T t) throws Exception;
}

