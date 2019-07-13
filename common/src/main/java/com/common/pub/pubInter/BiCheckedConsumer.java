package com.common.pub.pubInter;

@FunctionalInterface
public interface BiCheckedConsumer<T, U>{
    void accept(T t, U u) throws Exception;
}

